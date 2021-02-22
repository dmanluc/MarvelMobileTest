package dev.dmanluc.openbankmobiletest.data.datasource

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.BuildConfig
import dev.dmanluc.openbankmobiletest.data.api.MarvelApi
import dev.dmanluc.openbankmobiletest.data.mapper.toDomainModel
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTracker
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTrackerImpl
import dev.dmanluc.openbankmobiletest.utils.hashMd5
import dev.dmanluc.openbankmobiletest.utils.orFalse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class CharactersRemoteDataSourceImpl(
    private val marvelApi: MarvelApi,
    private val apiManager: ApiManager,
) : CharactersRemoteDataSource {

    override suspend fun getCharacters(
        pagingLoadTracker: PagingLoadTracker
    ): Flow<Either<ApiError, List<Character>>> {
        return apiManager.performNetworkRequest(
            localDataQuery = { performDatabaseLoadQuery(pagingLoadTracker) },
            fetchFromNetwork = { performNetworkApiCall(pagingLoadTracker) },
            saveFetchResult = ::performDatabaseSaveApiResult,
            shouldFetch = ::checkApiFetching,
        )
    }

    private fun performDatabaseLoadQuery(pagingLoadTracker: PagingLoadTracker): Flow<List<Character>> {
        return flow { listOf<Character>() }
    }

    private suspend fun performNetworkApiCall(pagingLoadTracker: PagingLoadTracker): List<Character> {
        val apiKey = BuildConfig.MARVEL_API_KEY
        val apiSecret = BuildConfig.MARVEL_API_SECRET
        val timestamp = Date().time.toString()
        val hash = "$timestamp$apiSecret$apiKey".hashMd5()
        val pagingOffset = pagingLoadTracker.getPagingStatus().pagingOffset

        return marvelApi.getCharacters(apiKey, timestamp, hash, pagingOffset).toDomainModel()
    }

    private suspend fun performDatabaseSaveApiResult(characters: List<Character>) {

    }

    private fun checkApiFetching(savedCharactersFromDb: List<Character>?): Boolean {
        return savedCharactersFromDb?.isEmpty().orFalse()
    }

}