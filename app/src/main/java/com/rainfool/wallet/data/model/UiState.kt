package com.rainfool.wallet.data.model

/**
 * 钱包UI状态
 */
sealed class WalletUiState {
    object Loading : WalletUiState()
    data class Success(val dashboard: WalletDashboard) : WalletUiState()
    data class Error(val message: String) : WalletUiState()
    object Empty : WalletUiState()
}

/**
 * 货币项UI数据模型
 */
data class CurrencyUiItem(
    val currency: Currency,
    val balance: WalletBalance?,
    val exchangeRate: ExchangeRate?,
    val usdValue: Double
) 