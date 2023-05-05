package com.example.booklibrary.ui.home.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.booklibrary.R
import com.example.booklibrary.databinding.FragmentBookDetailsBinding
import com.example.booklibrary.databinding.FragmentBookListBinding
import com.example.booklibrary.ui.home.viewmodel.BookViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : Fragment() {

    //region Variables
    private lateinit var binding: FragmentBookListBinding
    private val viewModel: BookViewModel by activityViewModels()
    //endregion

    //region Override Methods
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_list, container, false)

        setupViews()
        setupObservers()

        return binding.root
    }
    //endregion

    //region Setup
    private fun setupViews() {
        binding.button.setOnClickListener {
            viewModel.getResult(binding.inputEditText.text.toString())
        }

        hideLoading()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state.isLoading) showLoading() else hideLoading()
                binding.state = state

                state.apiResponse?.apply {
                    binding.responseKind.visibility = if (kind == "") View.GONE else View.VISIBLE
                    binding.responseCount.visibility =
                        if (totalItems == 0) View.GONE else View.VISIBLE
                }

                if (state.errorMessage.isNotEmpty()) showError()
            }
        }
    }
    //endregion

    //region Navigation
    fun toDetail() {
        findNavController().navigate(R.id.bookListToBookDetails)
    }
    //endregion

    //region View Methods
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showError(message: String? = "") {
        Snackbar.make(
            binding.progressBar,
            if (!message.isNullOrEmpty()) message else viewModel.uiState.value.errorMessage,
            Snackbar.LENGTH_LONG
        )
            .setTextColor(Color.WHITE)
            .setActionTextColor(Color.CYAN)
            .show()
    }
    //endregion
}