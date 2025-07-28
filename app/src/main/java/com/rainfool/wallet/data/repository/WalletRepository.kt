package com.rainfool.wallet.data.repository

import com.rainfool.wallet.data.model.Currency
import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.WalletBalance
import com.rainfool.wallet.data.network.WalletApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Wallet data repository
 * Manages all data operations, including network requests and local caching
 */
class WalletRepository(
    private val walletApi: WalletApi
) {
    
    /**
     * Get currency list
     * Currency list is relatively static, only needs to be requested once
     */
    suspend fun getCurrencies(): Result<List<Currency>> {
        return try {
            val response = walletApi.getCurrencies()
            if (response.ok) {
                Result.success(response.currencies)
            } else {
                Result.failure(Exception("Failed to get currency list"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get real-time exchange rates
     */
    fun getExchangeRates(): Flow<Result<List<ExchangeRate>>> = flow {
        try {
            val response = walletApi.getLiveRates()
            if (response.ok) {
                emit(Result.success(response.tiers))
            } else {
                emit(Result.failure(Exception("Failed to get exchange rates")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    /**
     * Get wallet balances
     */
    fun getWalletBalances(): Flow<Result<List<WalletBalance>>> = flow {
        try {
            val response = walletApi.getWalletBalance()
            if (response.ok) {
                emit(Result.success(response.wallet))
            } else {
                emit(Result.failure(Exception("Failed to get wallet balances")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
} 