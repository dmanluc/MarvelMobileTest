package dev.dmanluc.marvelmobiletest.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.dmanluc.marvelmobiletest.domain.model.UrlItem
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.reflect.Type
import java.util.*

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Specific Room type converter for domain character entity to be able to get it and save it from/to database
 *
 */
class CharacterEntityConverter : KoinComponent {

    private val gson: Gson by inject()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromStringToUrlItemList(jsonData: String?): List<UrlItem?>? {
        jsonData ?: return emptyList()

        val listType: Type = object : TypeToken<List<UrlItem?>?>() {}.type
        return gson.fromJson<List<UrlItem?>>(jsonData, listType)
    }

    @TypeConverter
    fun fromUrlItemListToString(urlItems: List<UrlItem?>?): String? {
        return gson.toJson(urlItems)
    }

}