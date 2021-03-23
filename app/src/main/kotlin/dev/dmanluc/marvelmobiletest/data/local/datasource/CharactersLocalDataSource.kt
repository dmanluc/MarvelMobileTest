package dev.dmanluc.marvelmobiletest.data.local.datasource

import dev.dmanluc.marvelmobiletest.domain.model.Character

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 *  Definition of a local source of data to manage characters data from a local app database
 */
interface CharactersLocalDataSource {

    suspend fun getCharacters(fromOffset: Int): List<Character>

    suspend fun saveCharacters(characters: List<Character>)

    suspend fun deleteAllCharacters()

    suspend fun replaceAllCharacters(characters: List<Character>)

}