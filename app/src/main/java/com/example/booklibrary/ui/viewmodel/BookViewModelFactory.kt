package com.example.booklibrary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.usecase.SearchBooksUC
import javax.inject.Inject

class BookViewModelFactory @Inject constructor(private val searchBooksUC: SearchBooksUC) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SearchBooksUC::class.java
        ).newInstance(searchBooksUC)
    }
}