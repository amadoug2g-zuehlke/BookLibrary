package com.example.booklibrary.ui.home.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.booklibrary.R
import com.example.booklibrary.databinding.FragmentBookDetailsBinding
import com.example.booklibrary.databinding.FragmentBookListBinding
import com.example.booklibrary.ui.home.viewmodel.BookViewModel

class BookListFragment : Fragment() {

    private lateinit var binding: FragmentBookListBinding
    private val viewModel: BookViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_list, container, false)

        binding.button.setOnClickListener {
            try {
                viewModel.getResult()
            } catch (e: Exception) {
                Log.e("TAG", "error?: $e®")
            }
        }

        return binding.root
    }

    fun toDetail() {
        findNavController().navigate(R.id.bookListToBookDetails)
    }
}