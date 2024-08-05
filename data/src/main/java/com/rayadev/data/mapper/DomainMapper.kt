package com.rayadev.data.mapper

import com.rayadev.data.database.CharacterEntity
import com.rayadev.domain.models.Character

interface CharacterEntityMapper {
    fun mapFromEntity(entity: Character): CharacterEntity
    fun mapToEntity(domain: CharacterEntity): CharacterEntity
}
