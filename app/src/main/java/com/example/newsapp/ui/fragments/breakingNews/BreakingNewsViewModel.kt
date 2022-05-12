package com.example.newsapp.ui.fragments.breakingNews

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

class BreakingNewsViewModel(val databaseRepository: DatabaseRepository, val apiRepository: ApiRepository ) : ViewModel() {

    val breakingNewsLiveData:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage=1



    init {
        getBreakingNews("eg")
    }


    fun getBreakingNews(countryCode:String)=viewModelScope.launch {
        breakingNewsLiveData.postValue(Resource.Loading())
        val response=apiRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNewsLiveData.postValue(handleBreakingNewsResponse(response))
    }



    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse > {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }




    
}