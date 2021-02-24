package dev.dmanluc.openbankmobiletest.domain.repository

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {

    suspend fun getCharacters(pagingOffset: Int): Flow<Either<ApiError, List<Character>>>

}