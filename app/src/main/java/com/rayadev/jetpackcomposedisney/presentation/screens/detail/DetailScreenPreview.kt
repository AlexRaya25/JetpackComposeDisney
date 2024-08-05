package com.rayadev.jetpackcomposedisney.presentation.screens.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rayadev.data.database.CharacterEntity

@Preview(showBackground = true)
@Composable
fun DetailScreenContentPreview() {
    val exampleCharacter = CharacterEntity(
        id = 1,
        name = "Example Character",
        imageUrl = "https://via.placeholder.com/300",
        films = listOf("Film 1", "Film 2"),
        tvShows = listOf("TV Show 1", "TV Show 2"),
        videoGames = listOf("Video Game 1", "Video Game 2"),
        parkAttractions = listOf("Park Attraction 1", "Park Attraction 2")
    )

    DetailScreenContent(
        onUpClick = {},
        characterData = exampleCharacter,
        loading = false
    )
}