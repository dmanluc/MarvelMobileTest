package dev.dmanluc.openbankmobiletest.domain.repository

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.data.datasource.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {

    suspend fun getCharacters(forceRefresh: Boolean = false): Flow<Either<ApiError, List<Character>>>

}