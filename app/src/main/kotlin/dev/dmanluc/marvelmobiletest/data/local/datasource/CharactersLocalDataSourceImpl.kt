package dev.dmanluc.marvelmobiletest.data.local.datasource

import dev.dmanluc.marvelmobiletest.data.local.dao.CharactersDao
import dev.dmanluc.marvelmobiletest.data.local.mapper.toDatabaseEntity
import dev.dmanluc.marvelmobiletest.data.local.mapper.toDomainModel
import dev.dmanluc.marvelmobiletest.domain.model.Character

class CharactersLocalDataSourceImpl(
    private val charactersDao: CharactersDao
) : CharactersLocalDataSource {

    override suspend fun getCharacters(fromOffset: Int): List<Character> {
        return charactersDao.getCharacters(fromOffset).map { it.toDomainModel() }
    }

    override suspend fun saveCharacters(characters: List<Character>) {
        val characterEntityList = characters.map { it.toDatabaseEntity() }
        charactersDao.insert(characterEntityList)
    }

    override suspend fun deleteAllCharacters() {
        charactersDao.deleteAllCharacters()
    }

    override suspend fun replaceAllCharacters(characters: List<Character>) {
        val characterEntityList = characters.map { it.toDatabaseEntity() }
        charactersDao.replaceAllCharacters(characterEntityList)
    }

}