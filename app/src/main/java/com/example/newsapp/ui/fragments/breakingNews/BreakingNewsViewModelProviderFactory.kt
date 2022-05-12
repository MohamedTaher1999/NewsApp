package com.example.newsapp.ui.fragments.breakingNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.repository.ApiRepository
import com.example.newsapp.data.repository.DatabaseRepository

class BreakingNewsViewModelProviderFactory(val databaseRepository: DatabaseRepository, val apiRepository: ApiRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =BreakingNewsViewModel(databaseRepository,apiRepository) as T
}