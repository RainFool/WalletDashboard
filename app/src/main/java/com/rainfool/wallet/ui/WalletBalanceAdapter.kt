package com.rainfool.wallet.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rainfool.wallet.R
import com.rainfool.wallet.data.model.WalletBalance
import com.rainfool.wallet.data.model.ExchangeRate

class WalletBalanceAdapter : RecyclerView.Adapter<WalletBalanceAdapter.BalanceViewHolder>() {
    
    private var balances: List<WalletBalance> = emptyList()
    private var exchangeRates: List<ExchangeRate> = emptyList()
    
    fun updateData(newBalances: List<WalletBalance>, newRates: List<ExchangeRate>) {
        balances = newBalances
        exchangeRates = newRates
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallet_balance, parent, false)
        return BalanceViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.bind(balances[position])
    }
    
    override fun getItemCount(): Int = balances.size
    
    inner class BalanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCurrency: TextView = itemView.findViewById(R.id.tvCurrency)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val tvUsdValue: TextView = itemView.findViewById(R.id.tvUsdValue)
        private val tvCurrencyIcon: TextView = itemView.findViewById(R.id.tvCurrencyIcon)
        
        fun bind(balance: WalletBalance) {
            tvCurrency.text = balance.currency
            tvAmount.text = String.format("%.8f", balance.amount)
            
            // 设置货币图标和颜色
            when (balance.currency) {
                "BTC" -> {
                    tvCurrencyIcon.text = "₿"
                    tvCurrencyIcon.setBackgroundColor(Color.parseColor("#F7931A"))
                }
                "ETH" -> {
                    tvCurrencyIcon.text = "Ξ"
                    tvCurrencyIcon.setBackgroundColor(Color.parseColor("#627EEA"))
                }
                "CRO" -> {
                    tvCurrencyIcon.text = "C"
                    tvCurrencyIcon.setBackgroundColor(Color.parseColor("#1E3A8A"))
                }
                else -> {
                    tvCurrencyIcon.text = balance.currency.firstOrNull()?.toString() ?: "?"
                    tvCurrencyIcon.setBackgroundColor(Color.parseColor("#666666"))
                }
            }
            
            // 计算USD价值
            val rate = exchangeRates.find { it.fromCurrency == balance.currency && it.toCurrency == "USD" }
            if (rate != null && rate.rates.isNotEmpty()) {
                val usdRate = rate.rates.first().rate.toDoubleOrNull() ?: 0.0
                val usdValue = balance.amount * usdRate
                tvUsdValue.text = "$${String.format("%.2f", usdValue)}"
            } else {
                tvUsdValue.text = "$0.00"
            }
        }
    }
} 