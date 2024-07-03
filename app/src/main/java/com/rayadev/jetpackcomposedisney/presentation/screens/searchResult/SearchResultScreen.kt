package com.rayadev.jetpackcomposedisney.presentation.screens.searchResult

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rayadev.jetpackcomposedisney.R
import com.rayadev.jetpackcomposedisney.presentation.screens.main.CharactersViewModel
import com.rayadev.jetpackcomposedisney.presentation.shared.CharacterCard
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.LightGray
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_DETAIL
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_SEARCH

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    navController: NavController,
    searchQuery: String?,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(searchQuery) {
        searchQuery?.let {
            viewModel.searchCharacters(it)
        }
    }

    BackHandler {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$searchQuery") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(DESTINATION_SEARCH) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        content = { padding ->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                if (state.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.no_character_found))
                    }
                } else {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(padding)
                            .background(LightGray),
                        columns = GridCells.Fixed(1)
                    ) {
                        items(state) { data ->
                            CharacterCard(
                                data = data,
                                onNavigate = {  navController.navigate(DESTINATION_DETAIL + "/${data.id}") },
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}