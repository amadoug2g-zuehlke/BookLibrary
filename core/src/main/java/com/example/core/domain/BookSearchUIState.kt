package com.example.core.domain

import com.example.core.domain.model.ApiResponse

data class BookSearchUIState(
    val query: String = "",
    val isLoading: Boolean = false,
    val apiResponse: ApiResponse? = null,
    val errorMessage: String = ""
)