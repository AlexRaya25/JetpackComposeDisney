package com.rayadev.jetpackcomposedisney.presentation.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.rayadev.jetpackcomposedisney.R
import com.rayadev.jetpackcomposedisney.data.local.CharacterEntity
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.Black
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.DarkGray
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun DetailScreen(
    id: Int,
    onUpClick: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val characterState = viewModel.characterDetails.collectAsState()
    val isLoadingState = viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onUpClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = { Text(text = "") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.background(Color.Transparent)
            )
        },
        content = {
            LaunchedEffect(id) {
                viewModel.loadCharacterDetails(id)
            }

            val character = characterState.value
            val isLoading = isLoadingState.value

            if (isLoading) {
                LoadingContent()
            } else {
                character?.let { characterData ->
                    CharacterDetails(characterData = characterData)
                } ?: run {
                    NoCharacterDetails()
                }
            }
        }
    )
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CharacterDetails(characterData: CharacterEntity) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Column {
            Image(
                painter = rememberImagePainter(characterData.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(characterData.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
            }
            HorizontalDivider(padding = PaddingValues(horizontal = 10.dp), background = DarkGray, height = 3.dp)
            CharacterDetailList(title = stringResource(R.string.films), items = characterData.films)
            CharacterDetailList(title = stringResource(R.string.tv_shows), items = characterData.tvShows)
            CharacterDetailList(title = stringResource(R.string.video_games), items = characterData.videoGames)
            CharacterDetailList(title = stringResource(R.string.park_attractions), items = characterData.parkAttractions)
        }
    }
}

@Composable
fun CharacterDetailList(title: String, items: List<String>) {
    Spacer(modifier = Modifier.height(16.dp))
    val filteredItems = items.filter { it.isNotEmpty() }
    if (filteredItems.isNotEmpty()) {
        Column(modifier = Modifier.padding(horizontal = 28.dp)) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            filteredItems.forEach { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .border(2.dp, Black, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        item,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
        }
    } else {
        // If items list is empty, do not display anything
    }
}


@Composable
fun NoCharacterDetails() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.no_character_detail_found))
    }
}

@Composable
fun HorizontalDivider(
    padding: PaddingValues = PaddingValues(horizontal = 20.dp),
    background: Color = LightGray,
    height: Dp = 2.dp) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .height(height)
            .background(background),
        color = background
    )
}