package dev.dmanluc.openbankmobiletest.domain.repository

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.data.remote.datasource.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTracker
import kotlinx.coroutines.flow.Flow

interface MarvelCharactersRepository {

    suspend fun getCharacters(pagingLoadTracker: PagingLoadTracker): Flow<Either<ApiError, List<Character>>>

}