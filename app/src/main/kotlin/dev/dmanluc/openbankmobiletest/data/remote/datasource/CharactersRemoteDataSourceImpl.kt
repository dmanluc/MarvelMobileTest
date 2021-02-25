package dev.dmanluc.openbankmobiletest.data.remote.datasource

import com.google.gson.Gson
import dev.dmanluc.openbankmobiletest.BuildConfig
import dev.dmanluc.openbankmobiletest.data.remote.api.MarvelApi
import dev.dmanluc.openbankmobiletest.data.remote.mapper.toDomainModel
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.utils.hashMd5
import java.util.*

class CharactersRemoteDataSourceImpl(
    private val marvelApi: MarvelApi,
    private val gson: Gson
) : CharactersRemoteDataSource {

    override suspend fun getCharacters(pagingOffset: Int): List<Character> {
        return performNetworkApiCall(pagingOffset)
    }

    private suspend fun performNetworkApiCall(pagingOffset: Int): List<Character> {
        val apiKey = BuildConfig.MARVEL_API_KEY
        val apiSecret = BuildConfig.MARVEL_API_SECRET
        val timestamp = Date().time.toString()
        val hash = "$timestamp$apiSecret$apiKey".hashMd5()

        return marvelApi.getCharacters(apiKey, timestamp, hash, pagingOffset).toDomainModel(gson)
    }

}