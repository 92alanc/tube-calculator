package com.alancamargo.tubecalculator.search.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.search.databinding.ActivitySearchBinding
import com.alancamargo.tubecalculator.search.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SearchActivity : AppCompatActivity() {

    private var _binding: ActivitySearchBinding? = null
    private val binding: ActivitySearchBinding
        get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
