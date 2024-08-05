package com.rayadev.usecase

import com.rayadev.domain.models.CharacterDetailResponse
import com.rayadev.domain.repositories.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): CharacterDetailResponse {
        return repository.getCharacter(id)
    }
}
