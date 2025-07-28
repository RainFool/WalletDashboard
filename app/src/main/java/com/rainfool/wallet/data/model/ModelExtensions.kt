package com.rainfool.wallet.data.model

/**
 * Data model extension functions
 */

// ==================== Currency Extension Methods ====================

/**
 * Get display icon URL
 */
fun Currency.getDisplayImageUrl(): String = colorfulImageUrl

/**
 * Get currency display name
 */
fun Currency.getDisplayName(): String = "$name ($symbol)"

/**
 * Check if currency is supported (BTC, ETH, CRO)
 */
fun Currency.isSupportedCurrency(): Boolean {
    return symbol in listOf("BTC", "ETH", "CRO")
}

/**
 * Get currency display priority (for sorting)
 */
fun Currency.getDisplayPriority(): Int {
    return when (symbol) {
        "BTC" -> 1
        "ETH" -> 2
        "CRO" -> 3
        else -> 999
    }
}

// ==================== ExchangeRate Extension Methods ====================

/**
 * Get current exchange rate (using first rate)
 */
fun ExchangeRate.getCurrentRate(): Double? {
    return rates.firstOrNull()?.rate?.toDoubleOrNull()
}

/**
 * Check if it's USD exchange rate
 */
fun ExchangeRate.isUsdRate(): Boolean {
    return toCurrency == "USD"
}

/**
 * Check if it's supported currency exchange rate
 */
fun ExchangeRate.isSupportedCurrencyRate(): Boolean {
    return fromCurrency in listOf("BTC", "ETH", "CRO") && isUsdRate()
}

// ==================== Rate Extension Methods ====================

/**
 * Get exchange rate value
 */
fun Rate.getRateValue(): Double? {
    return rate.toDoubleOrNull()
}

/**
 * Get amount value
 */
fun Rate.getAmountValue(): Double? {
    return amount.toDoubleOrNull()
}

// ==================== WalletBalance Extension Methods ====================

/**
 * Check if it's supported currency balance
 */
fun WalletBalance.isSupportedCurrency(): Boolean {
    return currency in listOf("BTC", "ETH", "CRO")
}

/**
 * Get formatted balance display
 */
fun WalletBalance.getFormattedAmount(): String {
    return when (currency) {
        "BTC" -> "%.8f".format(amount)
        "ETH" -> "%.6f".format(amount)
        "CRO" -> "%.2f".format(amount)
        else -> "%.2f".format(amount)
    }
}

/**
 * Get currency symbol
 */
fun WalletBalance.getCurrencySymbol(): String = currency

// ==================== WalletDashboard Extension Methods ====================

/**
 * Get supported currencies list (BTC, ETH, CRO)
 */
fun WalletDashboard.getSupportedCurrencies(): List<Currency> {
    return currencies.filter { it.isSupportedCurrency() }
}

/**
 * Get supported exchange rates list
 */
fun WalletDashboard.getSupportedExchangeRates(): List<ExchangeRate> {
    return exchangeRates.filter { it.isSupportedCurrencyRate() }
}

/**
 * Get supported wallet balances list
 */
fun WalletDashboard.getSupportedWalletBalances(): List<WalletBalance> {
    return walletBalances.filter { it.isSupportedCurrency() }
}

/**
 * Get currency info by symbol
 */
fun WalletDashboard.getCurrencyBySymbol(symbol: String): Currency? {
    return currencies.find { it.symbol == symbol }
}

/**
 * Get exchange rate by currency symbol
 */
fun WalletDashboard.getExchangeRateByCurrency(currency: String): ExchangeRate? {
    return exchangeRates.find { 
        it.fromCurrency == currency && it.isUsdRate() 
    }
}

/**
 * Get wallet balance by currency symbol
 */
fun WalletDashboard.getWalletBalanceByCurrency(currency: String): WalletBalance? {
    return walletBalances.find { it.currency == currency }
}

/**
 * Calculate USD value for specified currency
 */
fun WalletDashboard.calculateUsdValue(currency: String): Double {
    val balance = getWalletBalanceByCurrency(currency)
    val rate = getExchangeRateByCurrency(currency)
    
    if (balance != null && rate != null) {
        val currentRate = rate.getCurrentRate()
        if (currentRate != null) {
            return balance.amount * currentRate
        }
    }
    return 0.0
}

/**
 * Calculate total USD balance
 */
fun WalletDashboard.calculateTotalUsdBalance(): Double {
    return getSupportedWalletBalances().sumOf { balance ->
        calculateUsdValue(balance.currency)
    }
}

/**
 * Get formatted total USD balance
 */
fun WalletDashboard.getFormattedTotalUsdBalance(): String {
    return "$%.2f".format(calculateTotalUsdBalance())
}

/**
 * Check if data is complete
 */
fun WalletDashboard.isDataComplete(): Boolean {
    val supportedCurrencies = getSupportedCurrencies()
    val supportedRates = getSupportedExchangeRates()
    val supportedBalances = getSupportedWalletBalances()
    
    return supportedCurrencies.isNotEmpty() && 
           supportedRates.isNotEmpty() && 
           supportedBalances.isNotEmpty()
}

/**
 * Check if WalletDashboard contains valid data
 */
fun WalletDashboard.hasValidData(): Boolean {
    return isDataComplete() && calculateTotalUsdBalance() > 0
}

/**
 * Convert WalletDashboard to UI item list
 */
fun WalletDashboard.toCurrencyUiItems(): List<CurrencyUiItem> {
    return getSupportedCurrencies().map { currency ->
        val balance = getWalletBalanceByCurrency(currency.symbol)
        val rate = getExchangeRateByCurrency(currency.symbol)
        val usdValue = calculateUsdValue(currency.symbol)
        
        CurrencyUiItem(
            currency = currency,
            balance = balance,
            exchangeRate = rate,
            usdValue = usdValue
        )
    }
}

// ==================== CurrencyUiItem Extension Methods ====================

/**
 * Get formatted USD value
 */
fun CurrencyUiItem.getFormattedUsdValue(): String {
    return "$%.2f".format(usdValue)
}

/**
 * Get currency icon URL
 */
fun CurrencyUiItem.getCurrencyIconUrl(): String {
    return currency.getDisplayImageUrl()
}

/**
 * Get currency display name
 */
fun CurrencyUiItem.getCurrencyDisplayName(): String {
    return currency.getDisplayName()
}

/**
 * Get balance display
 */
fun CurrencyUiItem.getBalanceDisplay(): String {
    return balance?.getFormattedAmount() ?: "0.00"
}

/**
 * Check if has valid data
 */
fun CurrencyUiItem.hasValidData(): Boolean {
    return balance != null && exchangeRate != null
}

// ==================== General Extension Methods ====================

/**
 * Format number as currency display format
 */
fun Double.formatAsCurrency(currency: String): String {
    return when (currency) {
        "USD" -> "$%.2f".format(this)
        "BTC" -> "%.8f".format(this)
        "ETH" -> "%.6f".format(this)
        "CRO" -> "%.2f".format(this)
        else -> "%.2f".format(this)
    }
}

/**
 * Safely parse string to Double
 */
fun String.toDoubleOrZero(): Double {
    return this.toDoubleOrNull() ?: 0.0
}

/**
 * Check if string is valid number
 */
fun String.isValidNumber(): Boolean {
    return this.toDoubleOrNull() != null
} 