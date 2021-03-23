package dev.dmanluc.marvelmobiletest.domain.usecase

import arrow.core.Either
import dev.dmanluc.marvelmobiletest.domain.model.ApiError
import dev.dmanluc.marvelmobiletest.domain.model.Character
import dev.dmanluc.marvelmobiletest.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Use case to fetch characters from repository.
 *
 */
class GetCharactersUseCase(private val repository: CharactersRepository) {

    suspend operator fun invoke(
        pagingOffset: Int,
        forceRefresh: Boolean = false
    ): Flow<Either<ApiError, List<Character>>> {
        return repository.getCharacters(pagingOffset, forceRefresh)
    }

}