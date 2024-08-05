package com.rayadev.jetpackcomposedisney.presentation.screens.main

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rayadev.domain.models.Character

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator()
}

@Preview(showBackground = true)
@Composable
fun NoCharactersFoundPreview() {
    NoDataMessage()
}

@Preview(showBackground = true)
@Composable
fun SectionHeaderPreview() {
    SectionHeader(initial = 'A')
}

@Preview(showBackground = true)
@Composable
fun TopAppBarWithSearchPreview() {
    val navController = rememberNavController()
    TopAppBarWithSearch(navController)
}

@Preview(showBackground = true)
@Composable
fun CharactersListPreview() {
    val navController = rememberNavController()
    val characters = listOf(
        Character(
            id = 1,
            name = "Example Character",
            imageUrl = "https://via.placeholder.com/300",
            films = listOf("Film 1", "Film 2"),
            tvShows = listOf("TV Show 1", "TV Show 2"),
            videoGames = listOf("Video Game 1", "Video Game 2"),
            parkAttractions = listOf("Park Attraction 1", "Park Attraction 2")
        ),
        Character(
            id = 2,
            name = "Example Character",
            imageUrl = "https://via.placeholder.com/300",
            films = listOf("Film 1", "Film 2"),
            tvShows = listOf("TV Show 1", "TV Show 2"),
            videoGames = listOf("Video Game 1", "Video Game 2"),
            parkAttractions = listOf("Park Attraction 1", "Park Attraction 2")
        )
    )
    val scrollState = rememberLazyListState()
    CharacterList(characters, navController, scrollState)
}