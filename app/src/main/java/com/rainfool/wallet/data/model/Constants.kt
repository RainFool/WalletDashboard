package com.rainfool.wallet.data.model

/**
 * Data model related constants
 */
object WalletConstants {
    
    /**
     * Target currency (USD)
     */
    const val TARGET_CURRENCY = "USD"
    
    /**
     * Currency configuration object
     * 统一管理所有货币的配置信息
     */
    object CurrencyConfig {
        /**
         * Supported currencies list
         */
        val SUPPORTED_CURRENCIES = listOf("BTC", "ETH", "CRO")
        
        /**
         * Currency configurations
         */
        val CURRENCIES = mapOf(
            "BTC" to CurrencyInfo(
                symbol = "BTC",
                decimalPlaces = 8,
                formatPattern = "%.8f",
                displayPriority = 1
            ),
            "ETH" to CurrencyInfo(
                symbol = "ETH", 
                decimalPlaces = 6,
                formatPattern = "%.6f",
                displayPriority = 2
            ),
            "CRO" to CurrencyInfo(
                symbol = "CRO",
                decimalPlaces = 2,
                formatPattern = "%.2f", 
                displayPriority = 3
            ),
            "USD" to CurrencyInfo(
                symbol = "USD",
                decimalPlaces = 2,
                formatPattern = "$%.2f",
                displayPriority = 999
            )
        )
        
        /**
         * Default currency configuration
         */
        val DEFAULT_CURRENCY = CurrencyInfo(
            symbol = "UNKNOWN",
            decimalPlaces = 2,
            formatPattern = "%.2f",
            displayPriority = 999
        )
    }
    
    /**
     * Currency information data class
     */
    data class CurrencyInfo(
        val symbol: String,
        val decimalPlaces: Int,
        val formatPattern: String,
        val displayPriority: Int
    )

    /**
     * Get format pattern for currency
     */
    private fun getFormatPattern(currency: String): String {
        val currencyInfo = CurrencyConfig.CURRENCIES[currency] ?: CurrencyConfig.DEFAULT_CURRENCY
        return currencyInfo.formatPattern
    }

    /**
     * Format currency value with proper decimal places
     */
    fun formatCurrencyValue(value: Double, currency: String): String {
        val format = getFormatPattern(currency)
        return format.format(value)
    }
    
    /**
     * Format USD value with currency symbol
     */
    fun formatUsdValue(value: Double): String {
        return getFormatPattern("USD").format(value)
    }
} 