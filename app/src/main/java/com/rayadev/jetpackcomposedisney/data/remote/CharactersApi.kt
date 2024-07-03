package com.rayadev.jetpackcomposedisney.data.remote

import com.rayadev.jetpackcomposedisney.domain.model.CharacterDetailResponse
import com.rayadev.jetpackcomposedisney.domain.model.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterResponse

    @GET("character")
    suspend fun getCharactersByName(
        @Query("name") name: String
    ): CharacterResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterDetailResponse
}
