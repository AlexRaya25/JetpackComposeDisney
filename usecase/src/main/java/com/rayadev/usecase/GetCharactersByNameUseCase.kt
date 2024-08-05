package com.rayadev.usecase

import com.rayadev.domain.models.CharacterResponse
import com.rayadev.domain.repositories.CharacterRepository
import javax.inject.Inject

class GetCharactersByNameUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(name: String): CharacterResponse {
        return repository.getCharactersByName(name)
    }
}
