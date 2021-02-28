package dev.dmanluc.openbankmobiletest.data.remote.api.mapper

import com.google.gson.GsonBuilder
import dev.dmanluc.openbankmobiletest.data.remote.mapper.toDomainModel
import dev.dmanluc.openbankmobiletest.utils.MockDataProvider
import junit.framework.Assert.assertEquals
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test

/**
 * @author Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version 1
 */
class CharactersMapperTest {

    private val gson = GsonBuilder().create()

    @Test
    fun `map characters api response to domain character list`() {
        val mockMarketApiResponse = MockDataProvider.createMockCharactersApiResponse()

        val mapperResult = mockMarketApiResponse.toDomainModel(gson)

        assertThat(mapperResult, notNullValue())
        assertEquals(mapperResult.size, mockMarketApiResponse.data?.results?.size)

        with(mapperResult[0]) {
            assertEquals(this.name, "3-D Man")
            assertThat(this.description, isEmptyString())
            assertThat(this.modifiedDate, notNullValue())
            assertThat(this.comicsSummary.items, `is`(not(emptyList())))
        }

        with(mapperResult[1]) {
            assertEquals(this.name, "A-Bomb (HAS)")
            assertThat(this.description, not(isEmptyString()))
            assertThat(this.modifiedDate, notNullValue())
            assertThat(this.eventsSummary.items, `is`(emptyList()))
        }

        with(mapperResult[2]) {
            assertEquals(this.name, "A.I.M.")
            assertThat(this.description, not(isEmptyString()))
            assertThat(this.modifiedDate, notNullValue())
            assertThat(this.eventsSummary.items, `is`(emptyList()))
        }
    }

    @Test
    fun `map empty characters api response to domain character list`() {
        val mockCharactersApiEmptyResponse = MockDataProvider.createMockEmptyCharactersApiResponse()

        val mapperResult = mockCharactersApiEmptyResponse.toDomainModel(gson)

        assertThat(mapperResult, notNullValue())
        assertThat(mapperResult, `is`(emptyList()))
    }

}