package dev.dmanluc.marvelmobiletest.domain.model

import com.google.gson.annotations.SerializedName

enum class CharacterUrlType {
    @SerializedName("detail")
    DETAIL,
    @SerializedName("wiki")
    WIKI,
    @SerializedName("comiclink")
    COMIC,
    UNDEFINED
}