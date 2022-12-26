package com.alancamargo.tubecalculator.search.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alancamargo.tubecalculator.search.R
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
        setUpUi()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemAbout -> {
                viewModel.onAppInfoClicked()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpUi() {
        with(binding) {
            setUpOriginField()
            setUpDestinationField()

            btUp.setOnClickListener {  }
            btDown.setOnClickListener {  }
            btCalculate.setOnClickListener { viewModel.onCalculateClicked() }
        }
    }

    private fun ActivitySearchBinding.setUpOriginField() {
        edtOrigin.setOnEditorActionListener { textField, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchOrigin(textField.text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun ActivitySearchBinding.setUpDestinationField() {
        edtDestination.setOnEditorActionListener { textField, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchDestination(textField.text.toString())
                true
            } else {
                false
            }
        }
    }
}
