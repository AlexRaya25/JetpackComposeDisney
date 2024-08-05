package com.rayadev.data.mapper

import com.rayadev.data.database.CharacterEntity
import com.rayadev.domain.models.Character

class CharacterEntityToCharacterMapper : CharacterEntityMapper {

    override fun mapFromEntity(entity: Character): CharacterEntity {
        return CharacterEntity(
            id = entity.id,
            name = entity.name,
            films = entity.films,
            tvShows = entity.tvShows,
            videoGames = entity.videoGames,
            parkAttractions = entity.parkAttractions,
            imageUrl = entity.imageUrl
        )
    }

    override fun mapToEntity(domain: CharacterEntity): CharacterEntity {
        return CharacterEntity(
            id = domain.id,
            name = domain.name,
            films = domain.films,
            tvShows = domain.tvShows,
            videoGames = domain.videoGames,
            parkAttractions = domain.parkAttractions,
            imageUrl = domain.imageUrl
        )
    }
}
