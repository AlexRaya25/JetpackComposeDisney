package com.rayadev.jetpackcomposedisney.presentation.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.jetpackcomposedisney.data.local.CharacterDao
import com.rayadev.jetpackcomposedisney.data.local.CharacterEntity
import com.rayadev.jetpackcomposedisney.data.repository.CharactersRepository
import com.rayadev.jetpackcomposedisney.presentation.screens.main.toCharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterDao: CharacterDao,
    private val repository: CharactersRepository
) : ViewModel() {

    private val _characterDetails = MutableStateFlow<CharacterEntity?>(null)
    val characterDetails: StateFlow<CharacterEntity?> = _characterDetails.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadCharacterDetails(id: Int) {
        viewModelScope.launch {
            try {
                characterDao.getCharacterById(id).collect { player ->
                    if (player != null) {
                        _characterDetails.value = player
                        _isLoading.value = false
                    } else {
                        val apiResponse = repository.getCharacter(id)
                        val playerEntity = apiResponse.character.toCharacterEntity()
                        _characterDetails.value = playerEntity
                        repository.insertPlayers(listOf(playerEntity))
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DetailViewModel", "Error fetching character details: ${e.message}")
                _isLoading.value = false
            }
        }
    }
}
