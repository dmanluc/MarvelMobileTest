package dev.dmanluc.marvelmobiletest.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.GsonBuilder
import dev.dmanluc.marvelmobiletest.data.local.dao.CharactersDao
import dev.dmanluc.marvelmobiletest.utils.MockDataProvider
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import java.io.IOException

/**
 * @author Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version 1
 */
@RunWith(AndroidJUnit4::class)
class CharactersDaoTest : AutoCloseKoinTest() {

    private lateinit var charactersDao: CharactersDao
    private lateinit var database: AppDatabase

    private val mockCharacterEntities = MockDataProvider.createMockCharacterEntities()

    @Before
    fun setUp() {
        configureDi()
    }

    private fun configureDi() {
        startKoin { modules(listOf(configureLocalModuleTest(ApplicationProvider.getApplicationContext()))) }
        database = get()
        charactersDao = get()
    }

    private fun configureLocalModuleTest(context: Context) = module {
        single {
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .build()
        }

        single {
            GsonBuilder().create()
        }

        factory { get<AppDatabase>().charactersDao() }
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