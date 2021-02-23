package dev.dmanluc.openbankmobiletest.data.remote.datasource

import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTracker
import kotlinx.coroutines.flow.Flow

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 */
interface CharactersRemoteDataSource {

    suspend fun getCharacters(pagingLoadTracker: PagingLoadTracker): List<Character>

}