package dev.dmanluc.openbankmobiletest.data.remote.repository

import CoroutineTestRule
import arrow.core.Either
import com.google.gson.GsonBuilder
import dev.dmanluc.openbankmobiletest.data.local.dao.CharactersDao
import dev.dmanluc.openbankmobiletest.data.local.datasource.CharactersLocalDataSource
import dev.dmanluc.openbankmobiletest.data.local.datasource.CharactersLocalDataSourceImpl
import dev.dmanluc.openbankmobiletest.data.local.entity.CharacterEntity
import dev.dmanluc.openbankmobiletest.data.local.mapper.toDatabaseEntity
import dev.dmanluc.openbankmobiletest.data.local.mapper.toDomainModel
import dev.dmanluc.openbankmobiletest.data.remote.api.MarvelApi
import dev.dmanluc.openbankmobiletest.data.remote.datasource.CharactersRemoteDataSource
import dev.dmanluc.openbankmobiletest.data.remote.datasource.CharactersRemoteDataSourceImpl
import dev.dmanluc.openbankmobiletest.data.repository.CharactersRepositoryImpl
import dev.dmanluc.openbankmobiletest.data.repository.DataSourceManager
import dev.dmanluc.openbankmobiletest.domain.model.ApiError
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.repository.CharactersRepository
import dev.dmanluc.openbankmobiletest.utils.MockDataProvider
import io.kotlintest.assertions.arrow.either.shouldBeLeft
import io.kotlintest.assertions.arrow.either.shouldBeRight
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version 1
 */
@ExperimentalCoroutinesApi
class CharactersRepositoryImplTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val mockCharacters = MockDataProvider.createMockCharacterList()
    private val mockProductsEntities: List<CharacterEntity>
        get() {
            return mockCharacters.map { it.toDatabaseEntity() }
        }

    private val marvelApiService = mockk<MarvelApi>()
    private val gson = GsonBuilder().create()
    private val charactersDao = mockk<CharactersDao>(relaxed = true)

    private lateinit var flowResult: Either<ApiError, List<Character>>
    private lateinit var charactersRepository: CharactersRepository
    private lateinit var localDataSource: CharactersLocalDataSource
    private lateinit var remoteDataSource: CharactersRemoteDataSource
    private lateinit var dataSourceManager: DataSourceManager

    @Before
    fun setUp() {
        localDataSource = CharactersLocalDataSourceImpl(charactersDao)
        remoteDataSource = CharactersRemoteDataSourceImpl(marvelApiService, gson)
        dataSourceManager = DataSourceManager(gson)

        charactersRepository =
            CharactersRepositoryImpl(localDataSource, remoteDataSource, dataSourceManager)
    }

    @Test
    fun `get characters from remote API when no internet is available and no previous data saved in local database`() {
        val exception = Exception("No internet")
        coEvery { marvelApiService.getCharacters(any(), any(), any(), any()) } throws exception
        coEvery { charactersDao.getCharacters(any()) } returns listOf()

        coroutinesTestRule.testDispatcher.runBlockingTest {
            flowResult = charactersRepository.getCharacters(0).single()
        }

        flowResult shouldBeLeft ApiError.UnknownApiError(exception)

        coVerifyOrder {
            charactersDao.getCharacters(any())
            marvelApiService.getCharacters(any(), any(), any(), any())
        }
    }

    @Test
    fun `get characters from remote API and save them to local database`() {
        coEvery {
            marvelApiService.getCharacters(
                any(),
                any(),
                any(),
                any()
            )
        } returns MockDataProvider.createMockCharactersApiResponse()
        coEvery { charactersDao.getCharacters(any()) } returns listOf() andThen mockProductsEntities

        coroutinesTestRule.testDispatcher.runBlockingTest {
            flowResult = charactersRepository.getCharacters(0).single()
        }

        flowResult shouldBeRight mockCharacters

        coVerifyOrder {
            charactersDao.getCharacters(any())
            marvelApiService.getCharacters(any(), any(), any(), any())
        }

        coVerify(exactly = 1) {
            charactersDao.insert(mockProductsEntities)
        }
    }

    @Test
    fun `get characters from local database`() {
        coEvery { charactersDao.getCharacters(any()) } returns mockProductsEntities

        coroutinesTestRule.testDispatcher.runBlockingTest {
            flowResult = charactersRepository.getCharacters(0).single()
        }

        flowResult shouldBeRight mockProductsEntities.map { it.toDomainModel() }

        coVerifyOrder {
            charactersDao.getCharacters(any())
        }
    }

}