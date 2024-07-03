package com.rayadev.jetpackcomposedisney.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.jetpackcomposedisney.data.local.CharacterEntity
import com.rayadev.jetpackcomposedisney.data.repository.CharactersRepository
import com.rayadev.jetpackcomposedisney.domain.model.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _state = MutableStateFlow<List<CharacterEntity>>(emptyList())
    val state: StateFlow<List<CharacterEntity>>
        get() = _state

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    private val _selectedPlayer = MutableStateFlow<CharacterEntity?>(null)
    val selectedPlayer: StateFlow<CharacterEntity?>
        get() = _selectedPlayer

    private var currentPage = 1
    private var totalPages = 1

    fun fetchCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val localPlayers = repository.getAllPlayers().firstOrNull()

                if (localPlayers != null && localPlayers.isNotEmpty()) {
                    _state.value = localPlayers
                } else {

                    val response = repository.getCharacters(currentPage)
                    totalPages = response.info.totalPages
                    _state.value = _state.value + response.characters.map { it.toCharacterEntity() }

                    repository.insertPlayers(_state.value)
                }
            } catch (e: Exception) {
                e.printStackTrace()

            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchCharacters(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _state.value = emptyList()
            try {
                val response = repository.getCharactersByName(query)
                _state.value = response.characters.map { it.toCharacterEntity() }

            } catch (e: Exception) {
                e.printStackTrace()

            } finally {
                _isLoading.value = false
            }
        }
    }
}

fun Data.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name ?: "Unknown",
        imageUrl = imageUrl ?: "",
        films = films ?: emptyList(),
        tvShows = tvShows ?: emptyList(),
        videoGames = videoGames ?: emptyList(),
        parkAttractions = parkAttractions ?: emptyList()
    )
}
