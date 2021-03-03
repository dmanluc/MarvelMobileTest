package dev.dmanluc.openbankmobiletest.domain.repository

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import kotlinx.coroutines.flow.Flow

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Definition of a component via repository pattern from which the app should gather its data (characters from API (if not saved in database or user forces refresh) or from database)
 *
 */
interface CharactersRepository {

    suspend fun getCharacters(
        pagingOffset: Int,
        forceRefresh: Boolean = false
    ): Flow<Either<ApiError, List<Character>>>

}