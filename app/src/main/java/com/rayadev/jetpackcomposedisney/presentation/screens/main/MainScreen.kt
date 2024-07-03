package com.rayadev.jetpackcomposedisney.presentation.screens.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rayadev.jetpackcomposedisney.R
import com.rayadev.jetpackcomposedisney.data.local.CharacterEntity
import com.rayadev.jetpackcomposedisney.presentation.shared.CharacterCard
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.LightGray
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.White
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_DETAIL
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_SEARCH


@Composable
fun MainScreen(
    navController: NavController,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val scrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.fetchCharacters()
    }

    Scaffold(
        topBar = { TopAppBarWithSearch(navController) },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .background(White)
            ) {
                when {
                    isLoading -> LoadingIndicator()
                    state.isEmpty() -> NoCharactersFound()
                    else -> CharactersList(state, scrollState, navController)
                }
            }
        }
    )
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun NoCharactersFound() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.no_character_found))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharactersList(
    characters: List<CharacterEntity>,
    scrollState: LazyListState,
    navController: NavController
) {
    val groupedCharacters = characters.groupBy { it.name.first().uppercaseChar() }

    LazyColumn(state = scrollState) {
        groupedCharacters.forEach { (initial, characters) ->
            stickyHeader { SectionHeader(initial) }
            items(characters) { character ->
                CharacterCard(
                    data = character,
                    onNavigate = { navController.navigate("$DESTINATION_DETAIL/${character.id}") },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun SectionHeader(initial: Char) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp)
    ) {
        Text(
            text = initial.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithSearch(navController: NavController) {
    Surface(color = LightGray) {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
                IconButton(onClick = { navController.navigate(DESTINATION_SEARCH) }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                }
            }
        )
    }
}