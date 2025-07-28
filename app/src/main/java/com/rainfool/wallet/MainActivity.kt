package com.rainfool.wallet

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rainfool.wallet.ui.MainViewModel
import com.rainfool.wallet.ui.MainUiState
import com.rainfool.wallet.ui.WalletBalanceAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    
    private lateinit var tvMessage: TextView
    private lateinit var tvTotalValue: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var rvBalances: RecyclerView
    private lateinit var balanceAdapter: WalletBalanceAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        setupRecyclerView()
        observeViewModel()
    }
    
    private fun initViews() {
        tvMessage = findViewById(R.id.tvMessage)
        tvTotalValue = findViewById(R.id.tvTotalValue)
        progressBar = findViewById(R.id.progressBar)
        rvBalances = findViewById(R.id.rvBalances)
    }
    
    private fun setupRecyclerView() {
        balanceAdapter = WalletBalanceAdapter()
        rvBalances.layoutManager = LinearLayoutManager(this)
        rvBalances.adapter = balanceAdapter
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
            tvMessage.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
            
            // 更新总价值
            tvTotalValue.text = "$${String.format("%.2f", uiState.totalUsdValue)}"
            
            // 更新余额列表
            balanceAdapter.updateData(uiState.walletBalances, uiState.exchangeRates, uiState.currencies)
            
            // 更新消息
            tvMessage.text = uiState.message
            tvMessage.visibility = View.VISIBLE
        }
    }
}