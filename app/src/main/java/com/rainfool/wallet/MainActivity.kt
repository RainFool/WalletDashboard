package com.rainfool.wallet

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rainfool.wallet.ui.MainViewModel
import com.rainfool.wallet.ui.MainUiState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    
    private lateinit var tvMessage: TextView
    private lateinit var progressBar: ProgressBar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        observeViewModel()
    }
    
    private fun initViews() {
        tvMessage = findViewById(R.id.tvMessage)
        progressBar = findViewById(R.id.progressBar)
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                updateUI(uiState)
            }
        }
    }
    
    private fun updateUI(uiState: MainUiState) {
        if (uiState.isLoading) {
            progressBar.visibility = View.VISIBLE
            tvMessage.text = "正在加载..."
        } else {
            progressBar.visibility = View.GONE
            tvMessage.text = uiState.message
        }
    }
}