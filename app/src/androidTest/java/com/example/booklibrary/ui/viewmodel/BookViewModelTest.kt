package com.example.booklibrary.ui.viewmodel

import com.example.core.domain.model.ApiResponse
import com.example.core.domain.model.IndustryIdentifier
import com.example.core.domain.model.PanelizationSummary
import com.example.core.domain.model.ReadingModes
import com.example.core.domain.model.Volume
import com.example.core.domain.model.VolumeInfo
import com.example.core.usecase.SearchBooksUC
import com.example.core.utils.State
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookViewModelTest {

    // Create a mock SearchBooksUC object
    @Mock
    lateinit var searchBooksUC: SearchBooksUC

    // Create a BookViewModel instance with the mocked SearchBooksUC dependency
    private lateinit var bookViewModel: BookViewModel

    @Before
    fun setup() {
        // Initialize the ViewModel with the mocked dependency
        bookViewModel = BookViewModel(searchBooksUC)
    }

    @Test
    fun getResultCorrectInputQuery() = runBlocking {
        // Given a query string
        val query = "Harry Potter"

        // When getResult is called with the query string
        bookViewModel.getResult(query)

        // Then SearchBooksUC should be invoked with the same query string
        verify(searchBooksUC).invoke(query)
    }

    @Test
    fun getResultSuccessStateCorrectly() = runBlocking {
        // Given a query string
        val query = "Harry Potter"

        val authors = mutableListOf("author 1", "author 2")
        val industryId = IndustryIdentifier("type", "identifier")
        val listIndustryId = mutableListOf(industryId, industryId)
        val modes = ReadingModes(text = false, image = false)
        val summary = PanelizationSummary(false, containsImageBubbles = false)

        // Given a non-empty list of volumes
        val info = VolumeInfo(
            "title",
            authors,
            "2023",
            "Yes",
            listIndustryId,
            modes,
            100,
            "Yes",
            authors,
            5.0,
            1,
            "",
            false,
            "Yes", summary,
        )

        val volumes = listOf(
            Volume("Kind 1", "Id 1", "Etag 1", "1", info),
            Volume("Kind 2", "Id 2", "Etag 2", "2", info),
            Volume("Kind 3", "Id 3", "Etag 3", "3", info),
        )

        // And given that SearchBooksUC returns a Success state with some data
        val data = ApiResponse(kind = "books", totalItems = 3, items = volumes)
        `when`(searchBooksUC.invoke(query)).thenReturn(flowOf(State.Success(data)))

        // When getResult is called with the query string
        bookViewModel.getResult(query)

        // Then uiState should be updated with the same data
        assertEquals(data, bookViewModel.uiState.value.apiResponse)
    }

    @Test
    fun getResultFailedStateCorrectly() = runBlocking {
        // Given a query string
        val query = "Harry Potter"

        // And given that SearchBooksUC returns a Failed state with an error message
        val errorMessage = "Search failed"
        `when`(searchBooksUC.invoke(query)).thenReturn(flowOf(State.Failed(errorMessage)))

        // When getResult is called with the query string
        bookViewModel.getResult(query)

        // Then uiState should be updated with the same error message
        assertEquals(errorMessage, bookViewModel.uiState.value.errorMessage)
    }
}
