package com.rayadev.domain.models

data class Character(
    val id: Int,
    val name: String,
    val films: List<String> = emptyList(),
    val tvShows: List<String> = emptyList(),
    val videoGames: List<String> = emptyList(),
    val parkAttractions: List<String> = emptyList(),
    val imageUrl: String
)
