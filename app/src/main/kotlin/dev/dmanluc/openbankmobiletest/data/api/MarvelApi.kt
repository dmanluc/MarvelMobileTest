package dev.dmanluc.openbankmobiletest.data.api

import MarvelCharactersApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface MarvelApi {

    @GET("/v1/public/characters")
    fun getCharacters(): Response<MarvelCharactersApiResponse>

}