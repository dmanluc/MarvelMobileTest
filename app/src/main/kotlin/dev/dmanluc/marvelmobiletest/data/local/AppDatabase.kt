package dev.dmanluc.marvelmobiletest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.dmanluc.marvelmobiletest.data.local.dao.CharactersDao
import dev.dmanluc.marvelmobiletest.data.local.entity.CharacterEntity

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 *
 * Room app database declaration
 *
 */
@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    companion object {
        fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "MarvelCharacters.db"
        ).build()
    }

}