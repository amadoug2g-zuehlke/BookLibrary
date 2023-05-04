package com.example.core.data.repository

import com.example.core.domain.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BookRepository {
    suspend fun searchBooks(queryTerms: String): Response<ApiResponse>
}