package com.rayadev.jetpackcomposedisney.data.repository

import com.rayadev.jetpackcomposedisney.data.local.CharacterDao
import com.rayadev.jetpackcomposedisney.data.local.CharacterEntity
import com.rayadev.jetpackcomposedisney.data.remote.CharactersApi
import com.rayadev.jetpackcomposedisney.domain.model.CharacterDetailResponse
import com.rayadev.jetpackcomposedisney.domain.model.CharacterResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val api: CharactersApi,
    private val playerDao: CharacterDao
) {
    suspend fun getCharacters(page: Int): CharacterResponse {
        return try {
            api.getCharacters(page)
        } catch (e: Exception) {

            throw e
        }
    }

    suspend fun getCharactersByName(name: String): CharacterResponse {
        return try {
            api.getCharactersByName(name)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getCharacter(getCharacter: Int): CharacterDetailResponse {
        return api.getCharacter(getCharacter)
    }

    fun getAllPlayers(): Flow<List<CharacterEntity>> {
        return playerDao.getAllCharacters()
    }

    fun getPlayerById(id: Int): Flow<CharacterEntity> {
        return playerDao.getCharacterById(id)
    }

    suspend fun insertPlayers(players: List<CharacterEntity>) {
        try {
            playerDao.insertCharacters(players)
        } catch (e: Exception) {
            throw e
        }
    }
}
