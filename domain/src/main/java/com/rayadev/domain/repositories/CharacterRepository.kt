package com.rayadev.domain.repositories

import com.rayadev.domain.models.Character
import com.rayadev.domain.models.CharacterDetailResponse
import com.rayadev.domain.models.CharacterResponse
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(): CharacterResponse
    suspend fun getCharactersByName(name: String): CharacterResponse
    suspend fun getCharacter(id: Int): CharacterDetailResponse
    fun getAllCharacters(): Flow<List<Character>>
    suspend fun insertCharacters(characters: List<Character>)
}
