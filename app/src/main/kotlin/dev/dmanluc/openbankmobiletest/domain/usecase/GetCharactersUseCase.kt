package dev.dmanluc.openbankmobiletest.domain.usecase

import arrow.core.Either
import dev.dmanluc.openbankmobiletest.data.datasource.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTracker
import dev.dmanluc.openbankmobiletest.domain.repository.MarvelCharactersRepository
import kotlinx.coroutines.flow.Flow

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Use case to fetch characters from remote data source (if not saved in database or user forces refresh)
and save them to database. If saved, return them from database.
 *
 */
class GetCharactersUseCase(private val repository: MarvelCharactersRepository) {

    suspend operator fun invoke(pagingLoadTracker: PagingLoadTracker): Flow<Either<ApiError, List<Character>>> {
        return repository.getCharacters(pagingLoadTracker)
    }

}