package com.rayadev.data.repository

import android.util.Log
import com.rayadev.data.database.CharacterDao
import com.rayadev.data.database.CharacterEntity
import com.rayadev.data.server.CharactersApi
import com.rayadev.domain.models.Character
import com.rayadev.domain.models.CharacterDetailResponse
import com.rayadev.domain.models.CharacterResponse
import com.rayadev.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val api: CharactersApi,
    private val characterDao: CharacterDao
) : CharacterRepository {

    private val TAG = "CharactersRepository"

    override suspend fun getCharacters(): CharacterResponse {
        try {
            Log.d(TAG, "getCharacters: Fetching characters from API...")
            val response = api.getCharacters() // Suponiendo que siempre se busca la primera página

            // Ordenar los personajes por nombre y manejar listas nulas
            val sortedCharacters = response.characters
                .sortedBy { it.name }

            return response.copy(characters = sortedCharacters)
        } catch (e: Exception) {
            Log.e(TAG, "getCharacters: Error fetching characters", e)
            throw e
        }
    }

    override suspend fun getCharactersByName(name: String): CharacterResponse {
        try {
            Log.d(TAG, "getCharactersByName: Searching characters by name '$name'")
            val response = api.getCharactersByName(name)

            val sortedCharacters = response.characters
                .sortedBy { it.name }

            return response.copy(characters = sortedCharacters)
        } catch (e: Exception) {
            Log.e(TAG, "getCharactersByName: Error searching characters by name '$name'", e)
            throw e
        }
    }

    override suspend fun getCharacter(id: Int): CharacterDetailResponse {
        try {
            Log.d(TAG, "getCharacter: Fetching character with id $id")
            return api.getCharacter(id)
        } catch (e: Exception) {
            Log.e(TAG, "getCharacter: Error fetching character with id $id", e)
            throw e
        }
    }

    override fun getAllCharacters(): Flow<List<Character>> {
        Log.d(TAG, "getAllCharacters: Fetching all characters from local database")
        return characterDao.getAllCharacters().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun insertCharacters(characters: List<Character>) {
        Log.d(TAG, "insertCharacters: Inserting ${characters.size} characters into local database")
        val entities = characters.map { it.toEntity() }
        characterDao.insertCharacters(entities)
        Log.d(TAG, "insertCharacters: Characters inserted into local database successfully")
    }

    private fun CharacterEntity.toDomain(): Character {
        return Character(
            id = this.id,
            name = this.name,
            films = this.films,
            tvShows = this.tvShows,
            videoGames = this.videoGames,
            parkAttractions = this.parkAttractions,
            imageUrl = this.imageUrl
        )
    }

    private fun Character.toEntity(): CharacterEntity {
        return CharacterEntity(
            id = this.id,
            name = this.name,
            films = this.films,
            tvShows = this.tvShows,
            videoGames = this.videoGames,
            parkAttractions = this.parkAttractions,
            imageUrl = this.imageUrl
        )
    }
}
