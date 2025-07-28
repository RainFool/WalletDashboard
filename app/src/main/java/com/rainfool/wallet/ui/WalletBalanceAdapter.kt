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
import com.rainfool.wallet.data.util.ExchangeRateCalculator

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
            // Use unified formatting method
            tvAmount.text = "${WalletConstants.formatCurrencyValue(balance.amount, balance.currency)} ${balance.currency}"
            
            // Find corresponding currency information
            val currency = currencies.find { it.symbol == balance.currency }
            Log.d("WalletBalanceAdapter", "colorfulImageUrl: ${currency?.colorfulImageUrl}")
            // Load currency icon
            if (currency != null) {
                // Use colorful image URL
                Glide.with(itemView.context)
                    .load(currency.colorfulImageUrl)
                    .placeholder(R.drawable.currency_icon_background)
                    .error(R.drawable.currency_icon_background)
                    .into(ivCurrencyIcon)
            } else {
                // If currency information not found, use default icon
                ivCurrencyIcon.setImageResource(R.drawable.currency_icon_background)
            }
            
            // Calculate USD value using utility class
            val usdValue = ExchangeRateCalculator.calculateUsdValue(balance, exchangeRates)
            tvUsdValue.text = ExchangeRateCalculator.formatUsdValue(usdValue)
        }
    }
} 