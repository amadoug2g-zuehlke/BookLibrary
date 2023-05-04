package com.example.core.usecase

import com.example.core.data.repository.BookRepository

class SearchBooksUC(private val repository: BookRepository) {
    suspend operator fun invoke(queryTerms: String) = repository.searchBooks(queryTerms)
}