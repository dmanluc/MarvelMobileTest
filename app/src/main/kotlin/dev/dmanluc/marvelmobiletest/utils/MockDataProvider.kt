package dev.dmanluc.marvelmobiletest.utils

import androidx.annotation.VisibleForTesting
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dev.dmanluc.marvelmobiletest.data.local.entity.CharacterEntity
import dev.dmanluc.marvelmobiletest.data.local.mapper.toDatabaseEntity
import dev.dmanluc.marvelmobiletest.data.remote.mapper.toDomainModel
import dev.dmanluc.marvelmobiletest.data.remote.model.MarvelCharactersApiResponse
import dev.dmanluc.marvelmobiletest.domain.model.Character

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
    private const val MOCK_EMPTY_CHARACTERS_JSON_FILENAME = "mockEmptyCharactersApiResponse.json"
    private val gson = GsonBuilder().create()

    fun createMockCharacterList(): List<Character> {
        return parseMockJson<MarvelCharactersApiResponse>(MOCK_CHARACTERS_JSON_FILENAME)
            .toDomainModel(gson)
    }

    fun createMockCharactersApiResponse(): MarvelCharactersApiResponse {
        return parseMockJson(MOCK_CHARACTERS_JSON_FILENAME)
    }

    fun createMockEmptyCharactersApiResponse(): MarvelCharactersApiResponse {
        return parseMockJson(MOCK_EMPTY_CHARACTERS_JSON_FILENAME)
    }

    fun readJsonAsString(path: String): String {
        val stream = this.javaClass.classLoader?.getResourceAsStream(path) ?: return ""
        return stream.bufferedReader().use { bufferReader ->
            bufferReader.readText()
        }
    }

    fun createMockCharacterEntities(): List<CharacterEntity> {
        return createMockCharacterList().map { it.toDatabaseEntity() }
    }

    private inline fun <reified T> parseMockJson(jsonPath: String): T {
        val jsonAsString = readJsonAsString(jsonPath)
        return gson.fromJson(jsonAsString, object : TypeToken<T>() {}.type)
    }
}