package com.rayadev.jetpackcomposedisney.presentation.screens.search

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.rayadev.jetpackcomposedisney.R
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.LightGray
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.Red
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.White
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_MAIN
import com.rayadev.jetpackcomposedisney.utils.Constants.DESTINATION_SEARCH_RESULT
import com.rayadev.jetpackcomposedisney.utils.SearchHistoryManager

@Composable
fun SearchScreen(
    navController: NavController,
    onSearchSubmitted: (String) -> Unit
) {
    val context = LocalContext.current
    val searchTextState = remember { mutableStateOf("") }
    var searchHistory by rememberSaveable {
        mutableStateOf(
            SearchHistoryManager.getSearchHistory(
                context
            )
        )
    }
    var showErrorSnackbar by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var itemToDelete by rememberSaveable { mutableStateOf<String?>(null) }

    BackHandler {
        navController.navigate(DESTINATION_MAIN)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showErrorSnackbar) {
        if (showErrorSnackbar) {
            snackbarHostState.showSnackbar("Search cannot be empty.")
            showErrorSnackbar = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBarSearch(
                searchTextState = searchTextState,
                onSearchClicked = {
                    val searchText = searchTextState.value.trim()
                    if (searchText.isBlank()) {
                        showErrorSnackbar = true
                    } else {
                        onSearchSubmitted(searchText)
                        navController.navigate(DESTINATION_SEARCH_RESULT + "/${searchText}")

                        if (searchText.isNotBlank() && searchText !in searchHistory) {
                            searchHistory = mutableListOf(searchText) + searchHistory
                            SearchHistoryManager.saveSearch(searchText, context)
                        }
                    }
                },
                onBackClicked = {
                    navController.navigate(DESTINATION_MAIN)
                },
                focusRequester = focusRequester
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightGray)
                    .padding(padding)
            ) {
                LazyColumn {
                    items(searchHistory) { searchItem ->
                        SearchHistoryItem(
                            text = searchItem,
                            onDeleteClicked = {
                                searchHistory = searchHistory.toMutableList().apply {
                                    remove(searchItem)
                                }
                                SearchHistoryManager.clearSearchHistory(context)
                                searchHistory.forEach {
                                    SearchHistoryManager.saveSearch(it, context)
                                }
                            },
                            onItemSelected = {
                                searchTextState.value = it
                                val searchText = searchTextState.value
                                onSearchSubmitted(searchText)
                                navController.navigate(DESTINATION_SEARCH_RESULT + "/${searchText}")

                                if (searchText.isNotBlank() && searchText !in searchHistory) {
                                    searchHistory = mutableListOf(searchText) + searchHistory
                                    SearchHistoryManager.saveSearch(searchText, context)
                                }
                            },
                            onIconSelected = {
                                searchTextState.value = it
                            },
                            onLongClick = {
                                itemToDelete = searchItem
                                showDeleteDialog = true
                            }
                        )
                    }
                }

                if (showDeleteDialog && itemToDelete != null) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text(stringResource(R.string.confirm_deletion)) },
                        text = { Text(stringResource(R.string.delete_search_history)) },
                        confirmButton = {
                            TextButton(onClick = {
                                itemToDelete?.let { item ->
                                    searchHistory = searchHistory.toMutableList().apply {
                                        remove(item)
                                    }
                                    SearchHistoryManager.clearSearchHistory(context)
                                    searchHistory.forEach {
                                        SearchHistoryManager.saveSearch(it, context)
                                    }
                                }
                                showDeleteDialog = false
                                itemToDelete = null
                            }) {
                                Text(text = stringResource(R.string.delete), color = Red)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDeleteDialog = false
                                itemToDelete = null
                            }) {
                                Text(stringResource(R.string.cancel))
                            }
                        }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSearch(
    searchTextState: MutableState<String>,
    onSearchClicked: () -> Unit,
    onBackClicked: () -> Unit,
    focusRequester: FocusRequester
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(color = White) {
        TopAppBar(
            title = {
                TextField(
                    value = searchTextState.value,
                    onValueChange = { searchTextState.value = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            val searchText = searchTextState.value.trim()
                            if (searchText.isNotBlank()) {
                                onSearchClicked()
                                keyboardController?.hide()
                            }
                        }
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = Color.Transparent
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                }
            },
            actions = {
                IconButton(onClick = onSearchClicked) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search))
                }
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewTopAppBarSearch() {
    val searchTextState by remember { mutableStateOf("") }
    val focusRequester = FocusRequester()

    TopAppBarSearch(
        searchTextState = mutableStateOf(searchTextState),
        onSearchClicked = {},
        onBackClicked = {},
        focusRequester = focusRequester
    )
}
