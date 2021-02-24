package dev.dmanluc.openbankmobiletest.data.remote.datasource

import dev.dmanluc.openbankmobiletest.domain.model.Character

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 */
interface CharactersRemoteDataSource {

    suspend fun getCharacters(pagingOffset: Int): List<Character>

}