package com.example.newsapp.ui.fragments.searchNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.repository.ApiRepository
import com.example.newsapp.data.repository.DatabaseRepository

class SearchNewsViewModelProviderFactory(val databaseRepository: DatabaseRepository, val apiRepository: ApiRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =SearchNewsViewModel(databaseRepository,apiRepository) as T
}