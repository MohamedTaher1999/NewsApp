package com.example.newsapp.ui.fragments.searchNews

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

class SearchNewsViewModel(val databaseRepository: DatabaseRepository, val apiRepository: ApiRepository ) : ViewModel() {


    val searchNewsLiveData:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage=1





    fun searchNews(searchQuery: String)=viewModelScope.launch {
        searchNewsLiveData.postValue(Resource.Loading())
        val response=apiRepository.searchNews(searchQuery,searchNewsPage)
        searchNewsLiveData.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse > {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    
}