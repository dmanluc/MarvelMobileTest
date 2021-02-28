package dev.dmanluc.openbankmobiletest.utils

import androidx.annotation.VisibleForTesting
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.dmanluc.openbankmobiletest.data.remote.model.MarvelCharactersApiResponse
import dev.dmanluc.openbankmobiletest.domain.model.Character
import java.io.File

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Mock Data provider singleton for use in tests
 *
 */
@VisibleForTesting
object MockDataProvider {

    const val MOCK_CHARACTERS_JSON_FILENAME = "mockCharactersApiResponse.json"
    const val MOCK_EMPTY_CHARACTERS_JSON_FILENAME = "mockEmptyCharactersApiResponse.json"
    private val gson = GsonBuilder().create()

    fun createMockCharacterList(): List<Character> {
        return parseMockJson(MOCK_CHARACTERS_JSON_FILENAME)
    }

    fun createMockCharactersApiResponse(): MarvelCharactersApiResponse {
        return parseMockJson(MOCK_CHARACTERS_JSON_FILENAME)
    }

    fun createMockEmptyCharactersApiResponse(): MarvelCharactersApiResponse {
        return parseMockJson(MOCK_EMPTY_CHARACTERS_JSON_FILENAME)
    }

    fun readJsonAsString(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path.orEmpty())
        return String(file.readBytes())
    }

    private inline fun <reified T> parseMockJson(jsonPath: String): T {
        val jsonAsString = readJsonAsString(jsonPath)
        return gson.fromJson(jsonAsString, object : TypeToken<T>() {}.type)
    }
}