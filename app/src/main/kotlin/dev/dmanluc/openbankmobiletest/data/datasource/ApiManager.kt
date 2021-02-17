package dev.dmanluc.openbankmobiletest.data.datasource

import arrow.core.left
import arrow.core.right
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.dmanluc.openbankmobiletest.data.model.MarvelCharactersApiResponse
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException

class ApiManager(private val gson: Gson) {

    inline fun <ResultType, RequestType> performNetworkRequest(
        crossinline localDataQuery: () -> Flow<ResultType>,
        crossinline fetch: suspend () -> RequestType,
        crossinline saveFetchResult: suspend (RequestType) -> Unit,
        crossinline shouldFetch: (ResultType) -> Boolean = { true }
    ) = flow {
        val localData = localDataQuery().first()

        val flow = if (shouldFetch(localData)) {
            try {
                saveFetchResult(fetch())
                localDataQuery().map { it.right() }
            } catch (throwable: Throwable) {
                localDataQuery().map { handleException(throwable).left() }
            }
        } else {
            localDataQuery().map { it.right() }
        }

        emitAll(flow)
    }

    fun handleException(throwable: Throwable): ApiError {
        return when (throwable) {
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = convertErrorBody(throwable)
                ApiError.HttpError(code, errorResponse)
            }
            is IOException -> {
                ApiError.NetworkError(throwable)
            }
            else -> {
                ApiError.UnknownApiError(throwable)
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): String {
        val standardErrorMessage = throwable.response()?.errorBody()?.string().orEmpty()

        return try {
            throwable.response()?.errorBody()?.charStream()?.let {
                val apiResponse = gson.fromJson<MarvelCharactersApiResponse>(
                    it,
                    object : TypeToken<MarvelCharactersApiResponse>() {}.type
                )
                apiResponse.status
            } ?: standardErrorMessage
        } catch (exception: Exception) {
            standardErrorMessage
        }
    }

}