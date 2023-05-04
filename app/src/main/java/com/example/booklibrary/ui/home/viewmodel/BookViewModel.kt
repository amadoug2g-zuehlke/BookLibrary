package com.example.booklibrary.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.booklibrary.framework.network.repository.BookRepositoryImpl
import com.example.core.domain.model.ApiResponse
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepositoryImpl) : ViewModel() {
    //region Variables
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    private val _searchResult = MutableLiveData<ApiResponse>()
    val searchResult: LiveData<ApiResponse> = _searchResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    //endregion

    //region Functions
    fun getResult() {
        viewModelScope.launch {
            val response = repository.searchBooks("Agatha Christie")

            when (response.code()) {
                200 -> {
                    _searchResult.value = response.body()
                    Log.i("TAG", "result: ${_searchResult.value}")
                }
                else -> {
                    _errorMessage.value = response.errorBody().toString()
                    Log.i("TAG", "result: ${_errorMessage.value}")
                }
            }
        }
    }
    //endregion

    //region Observers
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