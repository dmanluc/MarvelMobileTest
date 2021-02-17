package dev.dmanluc.openbankmobiletest.data.datasource

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.domain.model.Character
import kotlinx.coroutines.flow.Flow

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 */
interface CharactersRemoteDataSource {

    suspend fun getCharacters(forceRefresh: Boolean): Flow<Either<ApiError, List<Character>>>

}