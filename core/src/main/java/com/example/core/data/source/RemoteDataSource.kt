package com.example.core.data.source

import com.example.core.domain.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {
    @GET("volumes")
    suspend fun searchBooks(@Query("q") queryTerms: String): Response<ApiResponse>
}