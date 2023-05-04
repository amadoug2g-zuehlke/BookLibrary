package com.example.booklibrary.framework.network

import com.example.core.utils.Constants.BASE_URL
import com.example.core.data.source.RemoteDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BookApi {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val BookService: RemoteDataSource by lazy {
        retrofit.create(RemoteDataSource::class.java)
    }
}

