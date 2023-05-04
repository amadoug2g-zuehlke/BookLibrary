package com.example.booklibrary.framework.network.repository

import com.example.core.data.repository.BookRepository
import com.example.core.data.source.RemoteDataSource
import com.example.core.domain.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class BookRepositoryImpl(private val remoteDataSource: RemoteDataSource) : BookRepository {
    override suspend fun searchBooks(queryTerms: String): Response<ApiResponse> {
        return remoteDataSource.searchBooks(queryTerms)
    }
}