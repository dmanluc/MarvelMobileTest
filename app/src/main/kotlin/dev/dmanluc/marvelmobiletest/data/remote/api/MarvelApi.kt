package dev.dmanluc.marvelmobiletest.data.remote.api

import dev.dmanluc.marvelmobiletest.data.remote.model.MarvelCharactersApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Marvel characters API definition
 *
 */
interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") time: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MarvelCharactersApiResponse

}