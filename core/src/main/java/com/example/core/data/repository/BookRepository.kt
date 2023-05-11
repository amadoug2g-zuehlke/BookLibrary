package com.example.core.data.repository

import com.example.core.domain.model.ApiResponse
import com.example.core.utils.State
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(queryTerms: String): Flow<State<ApiResponse>>
}