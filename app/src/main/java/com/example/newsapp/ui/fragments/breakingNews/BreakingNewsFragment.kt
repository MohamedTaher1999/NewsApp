package com.example.newsapp.ui.fragments.breakingNews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment() {

    lateinit var viewModel:BreakingNewsViewModel
    lateinit var breakingNewsAdapter:BreakingNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).breakingNewsViewModel
        setupRecyclerView()
        breakingNewsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleFragment,bundle)
        }
        viewModel.breakingNewsLiveData.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        breakingNewsAdapter.differ.submitList(newsResponse.articles)
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
        return view
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility=View.INVISIBLE

    }

    private fun showProgressBar(){
        paginationProgressBar.visibility=View.VISIBLE

    }
    private fun setupRecyclerView(){
        breakingNewsAdapter= BreakingNewsAdapter()
        rvBreakingNews.apply {
            adapter=breakingNewsAdapter
            layoutManager=LinearLayoutManager(activity)
        }

    }


}