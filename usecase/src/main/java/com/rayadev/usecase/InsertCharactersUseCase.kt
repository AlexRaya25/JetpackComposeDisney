package com.rayadev.usecase

import com.rayadev.domain.repositories.CharacterRepository
import javax.inject.Inject
import com.rayadev.domain.models.Character

class InsertCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    suspend operator fun invoke(characters: List<Character>) {
        repository.insertCharacters(characters)
    }
}

