package com.example.booklibrary.ui.home.viewmodel

import androidx.lifecycle.*
import com.example.booklibrary.framework.network.repository.BookRepositoryImpl
import com.example.core.domain.BookSearchUIState
import com.example.core.domain.model.ApiResponse
import com.example.core.usecase.SearchBooksUC
import com.example.core.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val searchBooksUC: SearchBooksUC) : ViewModel() {
    //region Variables
    private val _uiState = MutableStateFlow(BookSearchUIState())
    val uiState: StateFlow<BookSearchUIState> = _uiState.asStateFlow()
    //endregion

    //region Functions
    init {
        _uiState.update { state -> state.copy(isLoading = false, errorMessage = "") }
    }

    fun getResult(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                searchBooksUC.invoke(query).onStart {
                    setLoadingStatus(true)
                }.collect { state ->
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
                                    setErrorMessage("${result.errorBody()} (${result.code()})")
                                }
                            }
                        }
                        is State.Failed -> setErrorMessage(state.message)
                    }
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

class BookViewModelFactory @Inject constructor(private val searchBooksUC: SearchBooksUC) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SearchBooksUC::class.java
        ).newInstance(searchBooksUC)
    }
}