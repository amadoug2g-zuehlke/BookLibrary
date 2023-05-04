package com.example.core.domain.model

data class ApiResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<Volume>
)
