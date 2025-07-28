package com.rainfool.wallet.data.model

/**
 * 钱包仪表板聚合数据模型
 * 包含货币信息、汇率和余额的完整数据
 */
data class WalletDashboard(
    val currencies: List<Currency>,
    val exchangeRates: List<ExchangeRate>,
    val walletBalances: List<WalletBalance>
) 