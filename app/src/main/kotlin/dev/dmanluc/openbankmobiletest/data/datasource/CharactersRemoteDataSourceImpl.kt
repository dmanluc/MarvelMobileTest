package dev.dmanluc.openbankmobiletest.data.datasource

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.BuildConfig
import dev.dmanluc.openbankmobiletest.data.api.MarvelApi
import dev.dmanluc.openbankmobiletest.data.mapper.toDomainModel
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.utils.hashMd5
import dev.dmanluc.openbankmobiletest.utils.orFalse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class CharactersRemoteDataSourceImpl(
    private val marvelApi: MarvelApi,
    private val apiManager: ApiManager,
) : CharactersRemoteDataSource {

    companion object {
        const val DOT = "."
        const val IMAGE_MEDIUM_SIZE = "/standard_medium"
        const val IMAGE_BIG_SIZE = "/landscape_xlarge"
    }

    override suspend fun getCharacters(
        forceRefresh: Boolean,
        offset: Int
    ): Flow<Either<ApiError, List<Character>>> {
        return apiManager.performNetworkRequest(
            localDataQuery = { flow { listOf<Character>() } },
            fetchFromNetwork = {
                val apiKey = BuildConfig.MARVEL_API_KEY
                val apiSecret = BuildConfig.MARVEL_API_SECRET
                val timestamp = Date().time.toString()
                val hash = "$timestamp$apiSecret$apiKey".hashMd5()

                marvelApi.getCharacters(apiKey, timestamp, hash, offset).toDomainModel()
            },
            saveFetchResult = {},
            shouldFetch = { items: List<Character>? -> items?.isEmpty().orFalse() },
        )
    }

}