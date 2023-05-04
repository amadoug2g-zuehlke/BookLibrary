package com.example.booklibrary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.booklibrary.databinding.ActivityMainBinding
import com.example.booklibrary.ui.home.viewmodel.BookViewModel
import com.example.booklibrary.framework.network.BookApi
import com.example.booklibrary.framework.network.repository.BookRepositoryImpl
import com.example.booklibrary.framework.network.source.RemoteDataSourceImpl
import com.example.booklibrary.ui.home.viewmodel.BookViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private var viewModel1 : BookViewModel by activityViewModels()
    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupViewModel()
    }

    private fun setupViewModel() {
        val bookApi = BookApi
        val dataSource = RemoteDataSourceImpl(bookApi)
        val repository = BookRepositoryImpl(dataSource)

        val factory = BookViewModelFactory(repository)

        viewModel = ViewModelProvider(this,factory)[BookViewModel::class.java]
    }
}