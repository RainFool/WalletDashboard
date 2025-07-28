package com.rainfool.wallet.ui

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rainfool.wallet.R
import com.rainfool.wallet.data.model.WalletBalance
import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.Currency
import com.rainfool.wallet.data.model.WalletConstants

class WalletBalanceAdapter : RecyclerView.Adapter<WalletBalanceAdapter.BalanceViewHolder>() {
    
    private var balances: List<WalletBalance> = emptyList()
    private var exchangeRates: List<ExchangeRate> = emptyList()
    private var currencies: List<Currency> = emptyList()
    
    fun updateData(newBalances: List<WalletBalance>, newRates: List<ExchangeRate>, newCurrencies: List<Currency>) {
        balances = newBalances
        exchangeRates = newRates
        currencies = newCurrencies
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
        private val ivCurrencyIcon: ImageView = itemView.findViewById(R.id.ivCurrencyIcon)
        
        fun bind(balance: WalletBalance) {
            tvCurrency.text = balance.currency
            // 使用Constants中定义的小数位数显示金额
            val decimalPlaces = WalletConstants.getDecimalPlaces(balance.currency)
            tvAmount.text = String.format("%.${decimalPlaces}f %s", balance.amount, balance.currency)
            
            // 查找对应的货币信息
            val currency = currencies.find { it.symbol == balance.currency }
            Log.d("WalletBalanceAdapter", "colorfulImageUrl: ${currency?.colorfulImageUrl}")
            // 加载货币图标
            if (currency != null) {
                // 使用彩色图片URL
                Glide.with(itemView.context)
                    .load(currency.colorfulImageUrl)
                    .placeholder(R.drawable.currency_icon_background)
                    .error(R.drawable.currency_icon_background)
                    .into(ivCurrencyIcon)
            } else {
                // 如果没有找到货币信息，使用默认图标
                ivCurrencyIcon.setImageResource(R.drawable.currency_icon_background)
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