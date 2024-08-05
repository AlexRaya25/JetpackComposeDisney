package com.rayadev.jetpackcomposedisney.presentation.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.Black
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.White
import com.rayadev.domain.models.Character

@Composable
fun CharacterCard(
    character: Character,
    onNavigate: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigate(character.id) }
            .height(120.dp),
        shape = MaterialTheme.shapes.medium,
        color = White,
        tonalElevation = 10.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 120.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Text(
                    text = character.name,
                    color = Black,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterCardPreview() {
    val sampleData = Character(
        id = 1,
        name = "Sample Character",
        imageUrl = "https://via.placeholder.com/150",
        films = listOf("Sample Film 1", "Sample Film 2"),
        tvShows = listOf("Sample TV Show 1", "Sample TV Show 2"),
        videoGames = listOf("Sample Game 1", "Sample Game 2"),
        parkAttractions = listOf("Sample Park 1", "Sample Park 2")
    )
    CharacterCard(character = sampleData, onNavigate = {})
}
