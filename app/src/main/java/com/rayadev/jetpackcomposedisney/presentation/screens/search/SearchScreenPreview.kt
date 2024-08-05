package com.rayadev.jetpackcomposedisney.presentation.screens.search

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.tooling.preview.Preview

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

@Preview(showBackground = true)
@Composable
fun SearchHistoryItemPreview() {
    SearchHistoryItem(
        text = "Sample Search",
        onDeleteClicked = {},
        onItemSelected = {},
        onIconSelected = {},
        onLongClick = {}
    )
}