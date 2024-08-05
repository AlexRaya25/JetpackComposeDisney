package com.rayadev.jetpackcomposedisney.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.domain.models.Character
import com.rayadev.usecase.GetCharactersByNameUseCase
import com.rayadev.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersByNameUseCase: GetCharactersByNameUseCase
) : ViewModel() {

    private val TAG = "CharactersViewModel"

    private val _state = MutableStateFlow<List<Character>>(emptyList())
    val state: StateFlow<List<Character>>
        get() = _state

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d(TAG, "fetchCharacters: Loading characters from API...")
                val response = getCharactersUseCase()
                _state.value = response.characters
                Log.d(TAG, "fetchCharacters: Characters loaded from API.")
            } catch (e: Exception) {
                Log.e(TAG, "fetchCharacters: Error fetching characters", e)
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchCharacters(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d(TAG, "searchCharacters: Searching characters by name...")
                val response = getCharactersByNameUseCase(query)
                _state.value = response.characters
                Log.d(TAG, "searchCharacters: Characters found for query '$query'.")
            } catch (e: Exception) {
                Log.e(TAG, "searchCharacters: Error searching characters", e)
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
