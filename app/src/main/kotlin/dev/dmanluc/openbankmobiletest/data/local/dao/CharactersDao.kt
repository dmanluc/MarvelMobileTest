package dev.dmanluc.openbankmobiletest.data.local.dao

import androidx.room.*
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
abstract class CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(items: List<CharacterEntity>)

    @Query("SELECT * FROM marvelCharacters LIMIT 20 OFFSET :fromOffset")
    abstract suspend fun getCharacters(fromOffset: Int): List<CharacterEntity>

    @Query("DELETE FROM marvelCharacters")
    abstract fun deleteAllCharacters()

    @Transaction
    open suspend fun replaceAllCharacters(items: List<CharacterEntity>) {
        deleteAllCharacters()
        insert(items)
    }

}