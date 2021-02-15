package dev.dmanluc.openbankmobiletest.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarvelApiResponse(
    @SerialName("attributionHTML")
    val attributionHTML: String?,
    @SerialName("attributionText")
    val attributionText: String?,
    @SerialName("code")
    val code: String?,
    @SerialName("copyright")
    val copyright: String?,
    @SerialName("data")
    val data: Data?,
    @SerialName("etag")
    val etag: String?,
    @SerialName("status")
    val status: String?
)

@Serializable
data class Data(
    @SerialName("count")
    val count: String?,
    @SerialName("limit")
    val limit: String?,
    @SerialName("offset")
    val offset: String?,
    @SerialName("results")
    val results: List<Result>?,
    @SerialName("total")
    val total: String?
)

@Serializable
data class Result(
    @SerialName("comics")
    val comics: Collection?,
    @SerialName("description")
    val description: String?,
    @SerialName("events")
    val events: Collection?,
    @SerialName("id")
    val id: String?,
    @SerialName("modified")
    val modified: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("resourceURI")
    val resourceURI: String?,
    @SerialName("series")
    val series: Collection?,
    @SerialName("stories")
    val stories: Collection?,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerialName("urls")
    val urls: List<Url>?
)

@Serializable
data class Collection(
    @SerialName("available")
    val available: String?,
    @SerialName("collectionURI")
    val collectionURI: String?,
    @SerialName("items")
    val items: List<Item>?,
    @SerialName("returned")
    val returned: String?
)

@Serializable
data class Thumbnail(
    @SerialName("extension")
    val extension: String?,
    @SerialName("path")
    val path: String?
)

@Serializable
data class Url(
    @SerialName("type")
    val type: String?,
    @SerialName("url")
    val url: String?
)

@Serializable
data class Item(
    @SerialName("name")
    val name: String?,
    @SerialName("resourceURI")
    val resourceURI: String?,
    @SerialName("type")
    val type: String?
)