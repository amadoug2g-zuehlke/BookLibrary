package com.example.booklibrary.framework.di

import com.example.booklibrary.framework.network.BookApi
import com.example.booklibrary.framework.network.repository.BookRepositoryImpl
import com.example.booklibrary.framework.network.source.RemoteDataSourceImpl
import com.example.core.data.repository.BookRepository
import com.example.core.data.source.RemoteDataSource
import com.example.core.usecase.SearchBooksUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource = RemoteDataSourceImpl(BookApi)

    @Provides
    fun provideBookRepository(remoteDataSource: RemoteDataSource): BookRepository =
        BookRepositoryImpl(remoteDataSource)

    @Provides
    fun provideSearchBookUC(repository: BookRepository): SearchBooksUC = SearchBooksUC(repository)
}
