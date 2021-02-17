package dev.dmanluc.openbankmobiletest.data.datasource

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.data.api.MarvelApi
import dev.dmanluc.openbankmobiletest.data.mapper.toDomainModel
import dev.dmanluc.openbankmobiletest.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharactersRemoteDataSourceImpl(
    private val marvelApi: MarvelApi,
    private val apiManager: ApiManager
) : CharactersRemoteDataSource {

    companion object {
        const val DOT = "."
        const val IMAGE_MEDIUM_SIZE = "/standard_medium"
        const val IMAGE_BIG_SIZE = "/landscape_xlarge"
    }

    override suspend fun getCharacters(forceRefresh: Boolean): Flow<Either<ApiError, List<Character>>> {
        return apiManager.performNetworkRequest(
            localDataQuery = { flow { listOf<Character>() } },
            fetch = { marvelApi.getCharacters().toDomainModel() },
            saveFetchResult = {},
            shouldFetch = { items -> items.isNotEmpty() }
        )
    }

}