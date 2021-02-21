package dev.dmanluc.openbankmobiletest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Character(
    val id: String,
    val name: String,
    val description: String,
    val modifiedDate: Date?,
    val resourceURI: String,
    val thumbnail: String,
    val urls: List<UrlItem>?,
    val comicsSummary: SummaryList,
    val seriesSummary: SummaryList,
    val storiesSummary: SummaryList,
    val eventsSummary: SummaryList
) : Parcelable

@Parcelize
data class SummaryList(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<SummaryItem>
) : Parcelable

@Parcelize
data class SummaryItem(
    val name: String,
    val resourceURI: String
) : Parcelable

@Parcelize
data class UrlItem(
    val type: String,
    val url: String
) : Parcelable