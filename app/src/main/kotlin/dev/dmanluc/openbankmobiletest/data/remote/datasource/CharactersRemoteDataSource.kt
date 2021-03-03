package dev.dmanluc.openbankmobiletest.data.remote.datasource

import dev.dmanluc.openbankmobiletest.domain.model.Character

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of a remote source of data from which marvel characters data could be fetched
 *
 */
interface CharactersRemoteDataSource {

    suspend fun getCharacters(pagingOffset: Int): List<Character>

}