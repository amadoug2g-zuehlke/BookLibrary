package com.example.booklibrary.ui.viewmodel

import com.example.core.domain.BookSearchUIState
import com.example.core.domain.model.ApiResponse
import com.example.core.usecase.SearchBooksUC
import com.example.core.utils.State
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class BookViewModelTest {

    private lateinit var useCase: SearchBooksUC
    private lateinit var viewModel: BookViewModel
    private lateinit var apiResponse: ApiResponse
    private lateinit var apiError: String

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        useCase = mockk()
        viewModel = BookViewModel(useCase)
        apiResponse = mockk()
        apiError = "Error occurred"
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

    @Test
    fun `when invoke returns Success, uiState should be updated accordingly`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        //Given
        val expectedState = State.Success(apiResponse)
        coEvery { useCase.invoke(any()) } returns flowOf(expectedState)

        val uiStates = mutableListOf<BookSearchUIState>()
        val job = launch { viewModel.uiState.collect { uiStates.add(it) } }

        //When
        viewModel.getResult("valid query")

        //Then
        val lastState = uiStates.last()
        assertEquals(expectedState.data, lastState.apiResponse)
        assertEquals(false, lastState.isLoading)
        assertEquals("", lastState.errorMessage)

        job.cancel()
    }

    @Test
    fun `when invoke returns Failed, uiState should be updated accordingly`() =
        runTest(UnconfinedTestDispatcher()) {
            // Given
            val expectedState = State.failed<BookSearchUIState>(apiError)
            coEvery { useCase.invoke(any()) } returns flowOf(expectedState as State<ApiResponse>)

            val uiStates = mutableListOf<BookSearchUIState>()
            val job = launch { viewModel.uiState.collect { uiStates.add(it) } }

            // When
            viewModel.getResult("valid query")

            // Then
            val lastState = uiStates.last()
            assertEquals(null, lastState.apiResponse)
            assertEquals(false, lastState.isLoading)
            assertEquals("", lastState.errorMessage)

            job.cancel()
        }
}