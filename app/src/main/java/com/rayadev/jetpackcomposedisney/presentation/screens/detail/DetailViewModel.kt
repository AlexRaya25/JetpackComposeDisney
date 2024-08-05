package com.rayadev.jetpackcomposedisney.presentation.screens.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.data.database.CharacterEntity
import com.rayadev.data.mapper.CharacterEntityMapper
import com.rayadev.usecase.GetCharacterUseCase
import com.rayadev.usecase.InsertCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val insertCharactersUseCase: InsertCharactersUseCase,
    private val characterEntityMapper: CharacterEntityMapper
) : ViewModel() {

    private val _characterDetails = MutableStateFlow<CharacterEntity?>(null)
    val characterDetails: StateFlow<CharacterEntity?> = _characterDetails

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadCharacterDetails(id: Int) {
        viewModelScope.launch {
            try {
                val characterDetailResponse = getCharacterUseCase.invoke(id)
                val characterEntity = characterEntityMapper.mapFromEntity(characterDetailResponse.character)
                _characterDetails.value = characterEntity
                insertCharactersUseCase(listOf(characterDetailResponse.character))
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DetailViewModel", "Error fetching character details: ${e.message}")
                _isLoading.value = false
            }
        }
    }
}
