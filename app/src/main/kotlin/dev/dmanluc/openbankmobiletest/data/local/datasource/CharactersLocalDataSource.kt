package dev.dmanluc.openbankmobiletest.data.local.datasource

import dev.dmanluc.openbankmobiletest.domain.model.Character
import kotlinx.coroutines.flow.Flow

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 */
interface CharactersLocalDataSource {

    suspend fun getCharacters(fromOffset: Int): List<Character>

    suspend fun saveCharacters(characters: List<Character>)

    suspend fun deleteAllCharacters()

    suspend fun replaceAllCharacters(characters: List<Character>)

}