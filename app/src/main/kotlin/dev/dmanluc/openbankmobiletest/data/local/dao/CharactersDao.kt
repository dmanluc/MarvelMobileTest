package dev.dmanluc.openbankmobiletest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.dmanluc.openbankmobiletest.data.local.entity.CharacterEntity
import dev.dmanluc.openbankmobiletest.domain.model.Character
import kotlinx.coroutines.flow.Flow

/**
 * @author   Daniel Manrique Lucas <dmanluc91@gmail.com>
 * @version  1
 * @since    2019-08-31.
 *
 * Custom DAO for inserting and saving marvel characters
 *
 */
@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<CharacterEntity>)

    @Query("SELECT * FROM marvelCharacters LIMIT 20 OFFSET :fromOffset")
    suspend fun getCharacters(fromOffset: Int): List<CharacterEntity>

    @Query("DELETE FROM marvelCharacters")
    suspend fun deleteAllCharacters()

}