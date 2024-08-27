package com.rayadev.domain.models

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    val info: Info,
    @SerializedName("data")
    val characters: List<Character>?
)
