package dev.dmanluc.openbankmobiletest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.dmanluc.openbankmobiletest.data.local.converter.CharacterEntityConverter
import dev.dmanluc.openbankmobiletest.domain.model.UrlItem
import java.util.*

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 * @since    2019-08-31.
 *
 * Room database entity which models domain character
 *
 */
@Entity(tableName = "marvelCharacters")
@TypeConverters(CharacterEntityConverter::class)
data class CharacterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val modifiedDate: Date?,
    val resourceURI: String,
    val thumbnail: String,
    val urls: List<UrlItem>?,
    val comicsAvailable: Int,
    val seriesAvailable: Int,
    val storiesAvailable: Int,
    val eventsAvailable: Int,
)