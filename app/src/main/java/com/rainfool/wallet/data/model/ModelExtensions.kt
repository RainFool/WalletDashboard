package com.rainfool.wallet.data.model

/**
 * 数据模型扩展函数
 */

// ==================== Currency 扩展方法 ====================

/**
 * 获取显示用的图标URL
 */
fun Currency.getDisplayImageUrl(): String = colorfulImageUrl

/**
 * 获取货币的显示名称
 */
fun Currency.getDisplayName(): String = "$name ($symbol)"

/**
 * 检查是否为支持的货币（BTC、ETH、CRO）
 */
fun Currency.isSupportedCurrency(): Boolean {
    return symbol in listOf("BTC", "ETH", "CRO")
}

/**
 * 获取货币的显示优先级（用于排序）
 */
fun Currency.getDisplayPriority(): Int {
    return when (symbol) {
        "BTC" -> 1
        "ETH" -> 2
        "CRO" -> 3
        else -> 999
    }
}

// ==================== ExchangeRate 扩展方法 ====================

/**
 * 获取当前汇率（使用第一个rate）
 */
fun ExchangeRate.getCurrentRate(): Double? {
    return rates.firstOrNull()?.rate?.toDoubleOrNull()
}

/**
 * 检查是否为USD汇率
 */
fun ExchangeRate.isUsdRate(): Boolean {
    return toCurrency == "USD"
}

/**
 * 检查是否为支持的货币汇率
 */
fun ExchangeRate.isSupportedCurrencyRate(): Boolean {
    return fromCurrency in listOf("BTC", "ETH", "CRO") && isUsdRate()
}

// ==================== Rate 扩展方法 ====================

/**
 * 获取汇率数值
 */
fun Rate.getRateValue(): Double? {
    return rate.toDoubleOrNull()
}

/**
 * 获取数量数值
 */
fun Rate.getAmountValue(): Double? {
    return amount.toDoubleOrNull()
}

// ==================== WalletBalance 扩展方法 ====================

/**
 * 检查是否为支持的货币余额
 */
fun WalletBalance.isSupportedCurrency(): Boolean {
    return currency in listOf("BTC", "ETH", "CRO")
}

/**
 * 获取格式化的余额显示
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
 * 获取货币符号
 */
fun WalletBalance.getCurrencySymbol(): String = currency

// ==================== WalletDashboard 扩展方法 ====================

/**
 * 获取支持的货币列表（BTC、ETH、CRO）
 */
fun WalletDashboard.getSupportedCurrencies(): List<Currency> {
    return currencies.filter { it.isSupportedCurrency() }
}

/**
 * 获取支持的汇率列表
 */
fun WalletDashboard.getSupportedExchangeRates(): List<ExchangeRate> {
    return exchangeRates.filter { it.isSupportedCurrencyRate() }
}

/**
 * 获取支持的钱包余额列表
 */
fun WalletDashboard.getSupportedWalletBalances(): List<WalletBalance> {
    return walletBalances.filter { it.isSupportedCurrency() }
}

/**
 * 根据货币符号获取货币信息
 */
fun WalletDashboard.getCurrencyBySymbol(symbol: String): Currency? {
    return currencies.find { it.symbol == symbol }
}

/**
 * 根据货币符号获取汇率
 */
fun WalletDashboard.getExchangeRateByCurrency(currency: String): ExchangeRate? {
    return exchangeRates.find { 
        it.fromCurrency == currency && it.isUsdRate() 
    }
}

/**
 * 根据货币符号获取钱包余额
 */
fun WalletDashboard.getWalletBalanceByCurrency(currency: String): WalletBalance? {
    return walletBalances.find { it.currency == currency }
}

/**
 * 计算指定货币的USD价值
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
 * 计算总USD余额
 */
fun WalletDashboard.calculateTotalUsdBalance(): Double {
    return getSupportedWalletBalances().sumOf { balance ->
        calculateUsdValue(balance.currency)
    }
}

/**
 * 获取格式化的总USD余额
 */
fun WalletDashboard.getFormattedTotalUsdBalance(): String {
    return "$%.2f".format(calculateTotalUsdBalance())
}

/**
 * 检查数据是否完整
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
 * 检查WalletDashboard是否包含有效数据
 */
fun WalletDashboard.hasValidData(): Boolean {
    return isDataComplete() && calculateTotalUsdBalance() > 0
}

/**
 * 将WalletDashboard转换为UI项列表
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

// ==================== CurrencyUiItem 扩展方法 ====================

/**
 * 获取格式化的USD价值
 */
fun CurrencyUiItem.getFormattedUsdValue(): String {
    return "$%.2f".format(usdValue)
}

/**
 * 获取货币图标URL
 */
fun CurrencyUiItem.getCurrencyIconUrl(): String {
    return currency.getDisplayImageUrl()
}

/**
 * 获取货币显示名称
 */
fun CurrencyUiItem.getCurrencyDisplayName(): String {
    return currency.getDisplayName()
}

/**
 * 获取余额显示
 */
fun CurrencyUiItem.getBalanceDisplay(): String {
    return balance?.getFormattedAmount() ?: "0.00"
}

/**
 * 检查是否有有效数据
 */
fun CurrencyUiItem.hasValidData(): Boolean {
    return balance != null && exchangeRate != null
}

// ==================== 通用扩展方法 ====================

/**
 * 格式化数字为货币显示格式
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
 * 安全地解析字符串为Double
 */
fun String.toDoubleOrZero(): Double {
    return this.toDoubleOrNull() ?: 0.0
}

/**
 * 检查字符串是否为有效的数字
 */
fun String.isValidNumber(): Boolean {
    return this.toDoubleOrNull() != null
} 