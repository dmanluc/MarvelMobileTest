package dev.dmanluc.openbankmobiletest.data.remote.model

import com.google.gson.annotations.SerializedName

data class MarvelCharactersApiResponse(
    @SerializedName("attributionHTML")
    val attributionHTML: String?,
    @SerializedName("attributionText")
    val attributionText: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("copyright")
    val copyright: String?,
    @SerializedName("data")
    val data: Data?,
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("status")
    val status: String?
)

data class Data(
    @SerializedName("count")
    val count: String?,
    @SerializedName("limit")
    val limit: String?,
    @SerializedName("offset")
    val offset: String?,
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("total")
    val total: String?
)

data class Result(
    @SerializedName("comics")
    val comics: Collection?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("events")
    val events: Collection?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("modified")
    val modified: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("resourceURI")
    val resourceUri: String?,
    @SerializedName("series")
    val series: Collection?,
    @SerializedName("stories")
    val stories: Collection?,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("urls")
    val urls: List<Url>?
)

data class Collection(
    @SerializedName("available")
    val available: String?,
    @SerializedName("collectionURI")
    val collectionURI: String?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("returned")
    val returned: String?
)

data class Thumbnail(
    @SerializedName("extension")
    val extension: String?,
    @SerializedName("path")
    val path: String?
)

data class Url(
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)

data class Item(
    @SerializedName("name")
    val name: String?,
    @SerializedName("resourceURI")
    val resourceURI: String?,
    @SerializedName("type")
    val type: String?
)