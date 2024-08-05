package com.rayadev.usecase

import com.rayadev.domain.models.CharacterResponse
import com.rayadev.domain.repositories.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(): CharacterResponse {
        return repository.getCharacters()
    }
}
