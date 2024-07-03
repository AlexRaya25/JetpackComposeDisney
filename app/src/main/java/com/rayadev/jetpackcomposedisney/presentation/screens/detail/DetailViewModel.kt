package com.rayadev.jetpackcomposedisney.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.jetpackcomposedisney.data.local.CharacterDao
import com.rayadev.jetpackcomposedisney.data.local.CharacterEntity
import com.rayadev.jetpackcomposedisney.data.repository.CharactersRepository
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
                    _characterDetails.value = player
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()

                _isLoading.value = false
            }
        }
    }
}
