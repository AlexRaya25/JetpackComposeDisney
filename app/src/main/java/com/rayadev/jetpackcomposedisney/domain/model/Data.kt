package com.rayadev.jetpackcomposedisney.domain.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info") val info: Info,
    @SerializedName("data") val characters: List<Data>,
)

data class CharacterDetailResponse(
    @SerializedName("data") val character: Data
)

data class Info(
    @SerializedName("count") val count: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("previousPage") val previousPage: String?,
    @SerializedName("nextPage") val nextPage: String?
)

data class Data(
    @SerializedName("_id") val id: Int,
    @SerializedName("films") val films: List<String> = emptyList(),
    @SerializedName("shortFilms") val shortFilms: List<Any> = emptyList(),
    @SerializedName("tvShows") val tvShows: List<String> = emptyList(),
    @SerializedName("videoGames") val videoGames: List<String> = emptyList(),
    @SerializedName("parkAttractions") val parkAttractions: List<String> = emptyList(),
    @SerializedName("sourceUrl") val sourceUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("url") val url: String,
    @SerializedName("__v") val version: Int
)
