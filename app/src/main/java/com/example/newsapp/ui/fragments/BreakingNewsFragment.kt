package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Resource


class BreakingNewsFragment : Fragment() {

    lateinit var viewModel:NewsViewModel
    lateinit var newsAdapter:NewsAdapter
    lateinit var rvBreakingNews:RecyclerView
    lateinit var paginationProgressBar:ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        setupRecyclerView()
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->  
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }

                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { message->
                       Log.e("error","An Error")
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_breaking_news, container, false)
        rvBreakingNews=view.findViewById(R.id.rvBreakingNews)
        paginationProgressBar=view.findViewById(R.id.paginationProgressBar)
        return view
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility=View.INVISIBLE

    }

    private fun showProgressBar(){
        paginationProgressBar.visibility=View.VISIBLE

    }
    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
        }

    }


}