package dev.dmanluc.openbankmobiletest.data.repository

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.data.datasource.ApiError
import dev.dmanluc.openbankmobiletest.data.datasource.CharactersRemoteDataSource
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.flow.Flow

class MarvelCharactersRepositoryImpl(
    private val remoteDataSource: CharactersRemoteDataSource
) : MarvelCharactersRepository {

    override suspend fun getCharacters(forceRefresh: Boolean): Flow<Either<ApiError, List<Character>>> {
        return remoteDataSource.getCharacters(forceRefresh)
    }

}