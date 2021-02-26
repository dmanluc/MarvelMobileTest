package dev.dmanluc.openbankmobiletest.data.remote.api

import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import dev.dmanluc.openbankmobiletest.di.createNetworkModule
import dev.dmanluc.openbankmobiletest.utils.MockDataProvider
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.test.AutoCloseKoinTest

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 */
abstract class BaseApiTest : AutoCloseKoinTest() {

    protected lateinit var apiService: MarvelApi
    protected lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        configureMockServer()
        configureDi()
    }

    @After
    open fun tearDown() {
        stopMockServer()
    }

    private fun configureDi() {
        startKoin { modules(listOf(createNetworkModule(mockServer.url("/").toString()))) }
        apiService = get()
    }

    private fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    private fun stopMockServer() {
        mockServer.shutdown()
    }

    fun mockHttpResponse(mockServer: MockWebServer, fileName: String, responseCode: Int): Unit =
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(MockDataProvider.readJsonAsString(fileName))
        )

}