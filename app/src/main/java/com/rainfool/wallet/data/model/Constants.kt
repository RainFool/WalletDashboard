package com.rainfool.wallet.data.model

/**
 * Data model related constants
 */
object WalletConstants {
    /**
     * Supported currencies list
     */
    val SUPPORTED_CURRENCIES = listOf("BTC", "ETH", "CRO")
    
    /**
     * Target currency (USD)
     */
    const val TARGET_CURRENCY = "USD"
    
    /**
     * Default decimal places
     */
    const val DEFAULT_DECIMAL_PLACES = 2
    
    /**
     * BTC decimal places
     */
    const val BTC_DECIMAL_PLACES = 8
    
    /**
     * ETH decimal places
     */
    const val ETH_DECIMAL_PLACES = 6
    
    /**
     * CRO decimal places
     */
    const val CRO_DECIMAL_PLACES = 2
    
    /**
     * Get decimal places for currency
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
     * Check if currency is supported
     */
    fun isSupportedCurrency(currency: String): Boolean {
        return currency in SUPPORTED_CURRENCIES
    }
} 