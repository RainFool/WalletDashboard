package com.rainfool.wallet.data.network

import retrofit2.http.GET

/**
 * Wallet API interface
 * Defines network request methods for getting currencies, exchange rates and wallet balances
 */
interface WalletApi {
    
    /**
     * Get supported currency list
     */
    @GET("currencies")
    suspend fun getCurrencies(): CurrenciesResponse
    
    /**
     * Get real-time exchange rates
     */
    @GET("live-rates")
    suspend fun getLiveRates(): LiveRatesResponse
    
    /**
     * Get wallet balances
     */
    @GET("wallet-balance")
    suspend fun getWalletBalance(): WalletBalanceResponse
} 