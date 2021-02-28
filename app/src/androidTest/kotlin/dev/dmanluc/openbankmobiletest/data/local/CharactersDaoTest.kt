package dev.dmanluc.openbankmobiletest.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.dmanluc.openbankmobiletest.data.local.dao.CharactersDao
import dev.dmanluc.openbankmobiletest.utils.MockDataProvider
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CharactersDaoTest {

    private lateinit var charactersDao: CharactersDao
    private lateinit var database: AppDatabase

    private val mockCharacterEntities = MockDataProvider.createMockCharacterEntities()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        charactersDao = database.charactersDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun getCharactersFromDatabase_withPreviousCharactersSaved_returnThem() {
        fillDatabase()

        runBlocking {
            val characterEntities = database.charactersDao().getCharacters(0)
            assertEquals(characterEntities.size, mockCharacterEntities.size)
            assertEquals(mockCharacterEntities.first(), characterEntities.first())
        }
    }

    @Test
    fun getSavedCharactersFromDatabase_withNoPreviousCharactersSaved_returnEmptyList() =
        runBlocking {
            val characterEntities = database.charactersDao().getCharacters(0)
            assertThat(characterEntities, `is`(emptyList()))
        }

    @Test
    fun saveCharactersToDatabase_shouldUpdateDataInDatabase() = runBlocking {
        val newMockProductEntities =
            mockCharacterEntities.map { it.copy(name = it.name.toUpperCase()) }

        database.charactersDao().insert(newMockProductEntities)
        val newCharacterEntities = database.charactersDao().getCharacters(0)

        assertEquals(mockCharacterEntities.size, newCharacterEntities.size)
        assertNotEquals(mockCharacterEntities, newCharacterEntities)
    }

    @Test
    fun deleteAllCharactersFromDatabase_shouldDeleteAllCharactersData() {
        fillDatabase()

        runBlocking {
            database.charactersDao().deleteAllCharacters()
            val characterEntities = database.charactersDao().getCharacters(0)
            assertThat(characterEntities, `is`(emptyList()))
        }
    }

    private fun fillDatabase() {
        runBlocking {
            database.charactersDao().insert(mockCharacterEntities)
        }
    }

}