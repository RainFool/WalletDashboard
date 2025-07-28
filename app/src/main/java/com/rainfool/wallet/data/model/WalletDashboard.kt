package com.rainfool.wallet.data.model

/**
 * Wallet dashboard aggregated data model
 * Contains complete data including currency information, exchange rates and balances
 */
data class WalletDashboard(
    val currencies: List<Currency>,
    val exchangeRates: List<ExchangeRate>,
    val walletBalances: List<WalletBalance>
) 