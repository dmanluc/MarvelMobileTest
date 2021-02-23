package dev.dmanluc.openbankmobiletest.data.remote.repository

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.data.remote.datasource.ApiError
import dev.dmanluc.openbankmobiletest.data.remote.datasource.CharactersRemoteDataSource
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTracker
import dev.dmanluc.openbankmobiletest.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.flow.Flow

class MarvelCharactersRepositoryImpl(
    private val remoteDataSource: CharactersRemoteDataSource
) : MarvelCharactersRepository {

    override suspend fun getCharacters(
        pagingLoadTracker: PagingLoadTracker
    ): Flow<Either<ApiError, List<Character>>> {
        return remoteDataSource.getCharacters(pagingLoadTracker)
    }

}