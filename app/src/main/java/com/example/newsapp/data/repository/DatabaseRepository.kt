package com.example.newsapp.data.repository

import com.example.newsapp.data.api.RetrofitInstance
import com.example.newsapp.data.db.ArticleDatabase
import com.example.newsapp.data.models.Article

class DatabaseRepository (val db: ArticleDatabase) {



    suspend fun insert(article: Article)=db.getArticleDao().insertArticle(article)

    fun getSavedNews()=db.getArticleDao().getAllArticles()


    suspend fun deleteArticle(article: Article)=db.getArticleDao().deleteArticle(article)
}