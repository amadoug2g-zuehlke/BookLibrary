package com.example.booklibrary.framework.network.source

import com.example.booklibrary.framework.network.BookApi
import com.example.core.data.source.RemoteDataSource
import com.example.core.domain.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RemoteDataSourceImpl(private val apiService: BookApi): RemoteDataSource {
    override suspend fun searchBooks(queryTerms: String): ApiResponse {
        return apiService.BookService.searchBooks(queryTerms)
    }
}