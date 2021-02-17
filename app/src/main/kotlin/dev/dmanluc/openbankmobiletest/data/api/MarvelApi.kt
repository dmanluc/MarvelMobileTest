package dev.dmanluc.openbankmobiletest.data.api

import dev.dmanluc.openbankmobiletest.data.model.MarvelCharactersApiResponse
import retrofit2.http.GET

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(): MarvelCharactersApiResponse

}