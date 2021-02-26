package dev.dmanluc.openbankmobiletest.data.remote.api

import dev.dmanluc.openbankmobiletest.utils.MockDataProvider.MOCK_CHARACTERS_JSON_FILENAME
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection

/**
 * @author Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version 1
 */
class MarketApiTest : BaseApiTest() {

    @Test
    fun `success getting characters from marvel api`() {
        mockHttpResponse(mockServer, MOCK_CHARACTERS_JSON_FILENAME, HttpURLConnection.HTTP_OK)
        runBlocking {
            val productsApiResponse = apiService.getCharacters("", "", "", 0)
            with(productsApiResponse) {
                assertEquals(HttpURLConnection.HTTP_OK, code?.toInt())
                assertEquals(true, data != null)
                assertEquals(true, data?.results != null)
                assertEquals(4, data?.results?.size)
            }
        }
    }

    @Test(expected = HttpException::class)
    fun `error getting characters from marvel api with http bad request exception`() {
        mockHttpResponse(
            mockServer,
            MOCK_CHARACTERS_JSON_FILENAME,
            HttpURLConnection.HTTP_BAD_REQUEST
        )
        runBlocking {
            apiService.getCharacters("", "", "", 0)
        }
    }

}