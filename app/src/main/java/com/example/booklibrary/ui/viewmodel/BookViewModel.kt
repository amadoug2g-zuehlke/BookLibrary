package com.example.booklibrary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.BookSearchUIState
import com.example.core.domain.model.ApiResponse
import com.example.core.usecase.SearchBooksUC
import com.example.core.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val searchBooksUC: SearchBooksUC) : ViewModel() {

    //region Variables
    private val _uiState = MutableStateFlow(BookSearchUIState())
    val uiState: StateFlow<BookSearchUIState> = _uiState.asStateFlow()
    //endregion

    //region Functions
    fun getResult(query: String) {
        if (query.isNotEmpty()) {
            viewModelScope.launch {
                searchBooksUC.invoke(query).onStart {
                    setLoadingStatus(true)
                }.collect { state ->
                    when (state) {
                        is State.Loading -> setErrorMessage()
                        is State.Success<*> -> {
                            setErrorMessage()
                            setQuery(state.data as ApiResponse)
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
        if (message.isEmpty()) _uiState.update { state -> state.copy(errorMessage = message) }

        setLoadingStatus(false)
        _uiState.update { state -> state.copy(errorMessage = "") }
    }
    //endregion
}

