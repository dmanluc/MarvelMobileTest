package dev.dmanluc.openbankmobiletest.data.remote.api

import dev.dmanluc.openbankmobiletest.data.remote.model.MarvelCharactersApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("ts") time: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MarvelCharactersApiResponse

}