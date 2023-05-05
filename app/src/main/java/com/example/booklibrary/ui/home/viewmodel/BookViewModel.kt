package com.example.booklibrary.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.booklibrary.framework.network.repository.BookRepositoryImpl
import com.example.core.domain.BookSearchUIState
import com.example.core.domain.model.ApiResponse
import com.example.core.utils.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.http.Query

class BookViewModel(private val repository: BookRepositoryImpl) : ViewModel() {
    //region Variables
    private val _uiState = MutableStateFlow(BookSearchUIState())
    val uiState: StateFlow<BookSearchUIState> = _uiState.asStateFlow()
    //endregion

    //region Functions
    init {
        _uiState.update { state -> state.copy(isLoading = false, errorMessage = "") }
    }

    fun getResult(query: String) {
        viewModelScope.launch {
            val response = repository.searchBooks(query)

            response.collect { state ->
                when (state) {
                    is State.Loading -> setLoadingStatus(true)
                    is State.Success -> {
                        val result = state.data

                        when (result.code()) {
                            200 -> {
                                setLoadingStatus(false)
                                setQuery(result.body())
                            }
                            else -> {
                                setErrorMessage(result.errorBody().toString())
                            }
                        }
                    }
                    is State.Failed -> setErrorMessage(state.message)
                }
            }
        }
    }
    //endregion

    //region Observers
    private fun setLoadingStatus(value: Boolean) {
        _uiState.update { state -> state.copy(isLoading = value) }
    }

    private fun setQuery(query: ApiResponse?) {
        _uiState.update { state -> state.copy(apiResponse = query) }
    }

    private fun setErrorMessage(message: String = "") {
        _uiState.update { state -> state.copy(errorMessage = message) }
        setLoadingStatus(false)
        _uiState.update { state -> state.copy(errorMessage = "") }
    }
    //endregion
}

class BookViewModelFactory(private val repository: BookRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            BookRepositoryImpl::class.java
        ).newInstance(repository)
    }
}