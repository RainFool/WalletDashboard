package com.rainfool.wallet.data.model

/**
 * Wallet UI state
 */
sealed class WalletUiState {
    object Loading : WalletUiState()
    data class Success(val dashboard: WalletDashboard) : WalletUiState()
    data class Error(val message: String) : WalletUiState()
    object Empty : WalletUiState()
}

/**
 * Currency item UI data model
 */
data class CurrencyUiItem(
    val currency: Currency,
    val balance: WalletBalance?,
    val exchangeRate: ExchangeRate?,
    val usdValue: Double
) 