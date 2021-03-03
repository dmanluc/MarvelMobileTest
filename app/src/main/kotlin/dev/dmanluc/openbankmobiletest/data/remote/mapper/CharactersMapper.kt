package dev.dmanluc.openbankmobiletest.data.remote.mapper

import com.google.gson.Gson
import dev.dmanluc.openbankmobiletest.data.remote.model.MarvelCharactersApiResponse
import dev.dmanluc.openbankmobiletest.domain.model.*
import dev.dmanluc.openbankmobiletest.utils.enforceHttps
import dev.dmanluc.openbankmobiletest.utils.fromUTCTimeToDate
import dev.dmanluc.openbankmobiletest.utils.parseEnum
import java.util.*


internal fun MarvelCharactersApiResponse.toDomainModel(gson: Gson): List<Character> {
    val dotSymbol = "."

    return this.data?.results?.map { characterItem ->
        Character(
            id = characterItem.id.orEmpty(),
            name = characterItem.name.orEmpty(),
            description = characterItem.description.orEmpty(),
            resourceURI = characterItem.resourceUri.orEmpty(),
            modifiedDate = characterItem.modified?.fromUTCTimeToDate() ?: Date(),
            thumbnail = characterItem.thumbnail?.let {
                it.path + dotSymbol + it.extension
            }.enforceHttps().orEmpty(),
            urls = characterItem.urls?.map { urlItemResponse ->
                UrlItem(
                    type = gson.parseEnum(urlItemResponse.type.orEmpty())
                        ?: CharacterUrlType.UNDEFINED,
                    url = urlItemResponse.url.orEmpty()
                )
            },
            comicsSummary = SummaryList(
                available = characterItem.comics?.available?.toIntOrNull() ?: 0,
                returned = characterItem.comics?.returned?.toIntOrNull() ?: 0,
                collectionURI = characterItem.comics?.collectionURI.orEmpty(),
                items = characterItem.comics?.items?.map { com ->
                    SummaryItem(
                        name = com.name.orEmpty(),
                        resourceURI = com.resourceURI.orEmpty()
                    )
                }
                    ?: emptyList()),
            seriesSummary = SummaryList(
                available = characterItem.series?.available?.toIntOrNull() ?: 0,
                returned = characterItem.series?.returned?.toIntOrNull() ?: 0,
                collectionURI = characterItem.series?.collectionURI.orEmpty(),
                items = characterItem.series?.items?.map { com ->
                    SummaryItem(
                        name = com.name.orEmpty(),
                        resourceURI = com.resourceURI.orEmpty()
                    )
                }
                    ?: emptyList()),
            storiesSummary = SummaryList(
                available = characterItem.stories?.available?.toIntOrNull() ?: 0,
                returned = characterItem.stories?.returned?.toIntOrNull() ?: 0,
                collectionURI = characterItem.stories?.collectionURI.orEmpty(),
                items = characterItem.stories?.items?.map { com ->
                    SummaryItem(
                        name = com.name.orEmpty(),
                        resourceURI = com.resourceURI.orEmpty()
                    )
                }
                    ?: emptyList()),
            eventsSummary = SummaryList(
                available = characterItem.events?.available?.toIntOrNull() ?: 0,
                returned = characterItem.events?.returned?.toIntOrNull() ?: 0,
                collectionURI = characterItem.events?.collectionURI.orEmpty(),
                items = characterItem.events?.items?.map { com ->
                    SummaryItem(
                        name = com.name.orEmpty(),
                        resourceURI = com.resourceURI.orEmpty()
                    )
                }
                    ?: emptyList())
        )

    } ?: emptyList()
}