package com.rainfool.wallet.data.model

/**
 * 数据模型相关常量
 */
object WalletConstants {
    /**
     * 支持的货币列表
     */
    val SUPPORTED_CURRENCIES = listOf("BTC", "ETH", "CRO")
    
    /**
     * 目标货币（USD）
     */
    const val TARGET_CURRENCY = "USD"
    
    /**
     * 默认小数位数
     */
    const val DEFAULT_DECIMAL_PLACES = 2
    
    /**
     * BTC小数位数
     */
    const val BTC_DECIMAL_PLACES = 8
    
    /**
     * ETH小数位数
     */
    const val ETH_DECIMAL_PLACES = 6
    
    /**
     * CRO小数位数
     */
    const val CRO_DECIMAL_PLACES = 2
    
    /**
     * 获取货币的小数位数
     */
    fun getDecimalPlaces(currency: String): Int {
        return when (currency) {
            "BTC" -> BTC_DECIMAL_PLACES
            "ETH" -> ETH_DECIMAL_PLACES
            "CRO" -> CRO_DECIMAL_PLACES
            else -> DEFAULT_DECIMAL_PLACES
        }
    }
    
    /**
     * 检查是否为支持的货币
     */
    fun isSupportedCurrency(currency: String): Boolean {
        return currency in SUPPORTED_CURRENCIES
    }
} 