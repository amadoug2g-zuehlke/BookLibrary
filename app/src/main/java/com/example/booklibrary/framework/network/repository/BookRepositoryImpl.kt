package com.example.booklibrary.framework.network.repository

import com.example.core.data.repository.BookRepository
import com.example.core.data.source.RemoteDataSource
import com.example.core.utils.State
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BookRepositoryImpl(private val remoteDataSource: RemoteDataSource) : BookRepository {
    override suspend fun searchBooks(queryTerms: String) = flow {
        emit(State.loading())
        val data = remoteDataSource.searchBooks(queryTerms)

        emit(State.success(data))
    }.catch {
        emit(State.failed("An error occurred: ${it.localizedMessage}"))
    }.flowOn(IO)
}