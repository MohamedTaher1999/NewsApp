package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        var bottomNavigationView: BottomNavigationView =findViewById(R.id.bottomNavigationView)
        var newsNavHostFragment: FragmentContainerView =findViewById(R.id.newsNavHostFragment)
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())


    }
}