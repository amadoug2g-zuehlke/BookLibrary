package com.example.booklibrary.ui.home.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booklibrary.R
import com.example.booklibrary.databinding.FragmentBookListBinding
import com.example.booklibrary.ui.home.adapter.BookAction
import com.example.booklibrary.ui.home.adapter.BookVolumeAdapter
import com.example.booklibrary.ui.viewmodel.BookViewModel
import com.example.core.domain.model.VolumeInfo
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : Fragment(), BookAction {

    //region Variables
    private lateinit var binding: FragmentBookListBinding
    private val viewModel: BookViewModel by activityViewModels()
    private lateinit var bookVolumeAdapter: BookVolumeAdapter
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

    override fun onClick(volumeInfo: VolumeInfo) {
        Toast.makeText(requireContext(), "Item: ${volumeInfo.title}", Toast.LENGTH_SHORT).show()
        toDetail()
    }
    //endregion

    //region Setup
    private fun setupViews() {
        bookVolumeAdapter = BookVolumeAdapter(this)
        binding.apply {
            button.setOnClickListener {
                viewModel.getResult(binding.inputEditText.text.toString())
            }
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = bookVolumeAdapter
        }

        hideLoading()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.state = state
                if (state.isLoading) showLoading() else hideLoading()
                if (state.errorMessage.isNotEmpty()) showError()

                state.apiResponse?.apply {
                    items.let { bookVolumeAdapter.setData(it) }
                    binding.responseKind.visibility = if (kind == "") View.GONE else View.VISIBLE
                    binding.responseCount.visibility =
                        if (totalItems == 0) View.GONE else View.VISIBLE
                }

            }
        }
    }
    //endregion

    //region Navigation
    private fun toDetail() {
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