package com.example.newsapp.ui.fragments.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.NewsResponse
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.repository.ApiRepository
import com.example.newsapp.data.repository.DatabaseRepository

class ArticleViewModel(val databaseRepository: DatabaseRepository, val apiRepository: ApiRepository ) : ViewModel() {



    fun saveArticle(article : Article)=viewModelScope.launch{
        databaseRepository.insert(article)
    }

    
}