package dev.dmanluc.openbankmobiletest.data.repository

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.data.local.datasource.CharactersLocalDataSource
import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.data.remote.datasource.CharactersRemoteDataSource
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.repository.MarvelCharactersRepository
import dev.dmanluc.openbankmobiletest.utils.orFalse
import kotlinx.coroutines.flow.Flow

class MarvelCharactersRepositoryImpl(
    private val localDataSource: CharactersLocalDataSource,
    private val remoteDataSource: CharactersRemoteDataSource,
    private val dataSourceManager: CharactersDataSourceManager
) : MarvelCharactersRepository {

    override suspend fun getCharacters(pagingOffset: Int): Flow<Either<ApiError, List<Character>>> {
        return dataSourceManager.performDataRequest(
            localDataQuery = { loadCharactersFromLocal(pagingOffset) },
            fetchFromRemoteSource = { fetchCharactersFromRemote(pagingOffset) },
            saveFetchResult = ::saveRemoteDataToLocal,
            shouldFetch = ::checkIfRemoteSourceFetching,
        )
    }

    private suspend fun loadCharactersFromLocal(pagingOffset: Int): List<Character> {
        return localDataSource.getCharacters(pagingOffset)
    }

    private suspend fun fetchCharactersFromRemote(pagingOffset: Int): List<Character> {
        return remoteDataSource.getCharacters(pagingOffset)
    }

    private suspend fun saveRemoteDataToLocal(characters: List<Character>) {
        localDataSource.saveCharacters(characters)
    }

    private fun checkIfRemoteSourceFetching(savedCharactersFromLocalDataSource: List<Character>?): Boolean {
        return savedCharactersFromLocalDataSource?.isEmpty().orFalse()
    }

}