package dev.dmanluc.openbankmobiletest.data.remote.datasource

import dev.dmanluc.openbankmobiletest.BuildConfig
import dev.dmanluc.openbankmobiletest.data.local.toDatabaseEntity
import dev.dmanluc.openbankmobiletest.data.remote.api.MarvelApi
import dev.dmanluc.openbankmobiletest.data.remote.mapper.toDomainModel
import dev.dmanluc.openbankmobiletest.domain.model.Character
import dev.dmanluc.openbankmobiletest.domain.model.PagingLoadTracker
import dev.dmanluc.openbankmobiletest.utils.hashMd5
import dev.dmanluc.openbankmobiletest.utils.orFalse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class CharactersRemoteDataSourceImpl(
    private val marvelApi: MarvelApi,
) : CharactersRemoteDataSource {

    override suspend fun getCharacters(
        pagingLoadTracker: PagingLoadTracker
    ): List<Character> {
        return performNetworkApiCall(pagingLoadTracker)
    }

    private suspend fun performNetworkApiCall(pagingLoadTracker: PagingLoadTracker): List<Character> {
        val apiKey = BuildConfig.MARVEL_API_KEY
        val apiSecret = BuildConfig.MARVEL_API_SECRET
        val timestamp = Date().time.toString()
        val hash = "$timestamp$apiSecret$apiKey".hashMd5()
        val pagingOffset = pagingLoadTracker.getPagingStatus().pagingOffset

        return marvelApi.getCharacters(apiKey, timestamp, hash, pagingOffset).toDomainModel()
    }

}