package com.rayadev.domain.models

import com.google.gson.annotations.SerializedName

data class CharacterDetailResponse(
    @SerializedName(value = "data")
    val character: Character
)
