package com.rayadev.domain.models

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName(value = "_id")
    val id: Int,

    val name: String,
    val films: List<String> = emptyList(),
    val tvShows: List<String> = emptyList(),
    val videoGames: List<String> = emptyList(),
    val parkAttractions: List<String> = emptyList(),
    val imageUrl: String
)
