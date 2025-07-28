package com.rainfool.wallet.data.util

import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.WalletBalance
import com.rainfool.wallet.data.model.WalletConstants

/**
 * 汇率计算工具类
 * 提供统一的汇率计算和转换功能
 */
object ExchangeRateCalculator {
    
    /**
     * 计算单个钱包余额的USD价值
     * @param balance 钱包余额
     * @param exchangeRates 汇率列表
     * @return USD价值，如果无法计算则返回0.0
     */
    fun calculateUsdValue(balance: WalletBalance, exchangeRates: List<ExchangeRate>): Double {
        val rate = findUsdRate(balance.currency, exchangeRates)
        return if (rate != null) {
            balance.amount * rate
        } else {
            0.0
        }
    }
    
    /**
     * 计算总USD价值
     * @param balances 钱包余额列表
     * @param exchangeRates 汇率列表
     * @return 总USD价值
     */
    fun calculateTotalUsdValue(balances: List<WalletBalance>, exchangeRates: List<ExchangeRate>): Double {
        return balances.sumOf { balance ->
            calculateUsdValue(balance, exchangeRates)
        }
    }
    
    /**
     * 查找指定货币对USD的汇率
     * @param fromCurrency 源货币
     * @param exchangeRates 汇率列表
     * @return 汇率值，如果未找到则返回null
     */
    fun findUsdRate(fromCurrency: String, exchangeRates: List<ExchangeRate>): Double? {
        val rate = exchangeRates.find { 
            it.fromCurrency == fromCurrency && it.toCurrency == WalletConstants.TARGET_CURRENCY 
        }
        
        return if (rate != null && rate.rates.isNotEmpty()) {
            rate.rates.first().rate.toDoubleOrNull()
        } else {
            null
        }
    }
    
    /**
     * 格式化USD价值显示
     * @param usdValue USD价值
     * @return 格式化后的字符串
     */
    fun formatUsdValue(usdValue: Double): String {
        return WalletConstants.formatUsdValue(usdValue)
    }
    
    /**
     * 检查是否支持该货币的汇率计算
     * @param currency 货币代码
     * @return 是否支持
     */
    fun isSupportedCurrency(currency: String): Boolean {
        return WalletConstants.isSupportedCurrency(currency)
    }

}