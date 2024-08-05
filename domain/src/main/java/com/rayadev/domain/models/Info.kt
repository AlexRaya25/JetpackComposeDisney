package com.rayadev.domain.models

data class Info(
    val count: Int,
    val totalPages: Int,
    val previousPage: String?,
    val nextPage: String?
)
