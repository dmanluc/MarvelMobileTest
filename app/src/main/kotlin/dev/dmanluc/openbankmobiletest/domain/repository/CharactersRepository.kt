package dev.dmanluc.openbankmobiletest.domain.repository

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getCharacters(pagingOffset: Int, forceRefresh: Boolean = false): Flow<Either<ApiError, List<Character>>>

}