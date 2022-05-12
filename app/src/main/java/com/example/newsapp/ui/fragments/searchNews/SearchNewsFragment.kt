package com.example.newsapp.ui.fragments.searchNews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.newsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    lateinit var viewModel: SearchNewsViewModel
    lateinit var searchnewsAdapter: SearchNewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).searchNewsViewModel
        setupRecyclerView()
        searchnewsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,bundle)
        }

        var job: Job?=null
        etSearch.addTextChangedListener {
            job?.cancel()

            job= MainScope().launch {
                job= MainScope().launch {
                    delay(SEARCH_NEWS_TIME_DELAY)
                    etSearch.let {
                        if(etSearch.text.toString().isNotEmpty()){
                            viewModel.searchNews(etSearch.text.toString())
                        }
                    }
                }
            }
        }

        viewModel.searchNewsLiveData.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        searchnewsAdapter.differ.submitList(newsResponse.articles)
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
        val view:View= inflater.inflate(R.layout.fragment_search_news, container, false)

        return view
    }
    private fun hideProgressBar(){
        paginationProgressBar.visibility=View.INVISIBLE

    }

    private fun showProgressBar(){
        paginationProgressBar.visibility=View.VISIBLE

    }
    private fun setupRecyclerView(){
        searchnewsAdapter= SearchNewsAdapter()
        rvSearchNews.apply {
            adapter=searchnewsAdapter
            layoutManager= LinearLayoutManager(activity)
        }

    }
}