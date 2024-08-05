package com.rayadev.data.server

import com.rayadev.domain.models.CharacterDetailResponse
import com.rayadev.domain.models.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    @GET("character")
    suspend fun getCharacters(
    ): CharacterResponse

    @GET("character")
    suspend fun getCharactersByName(
        @Query("name") name: String
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterDetailResponse
}
