package com.rainfool.wallet.data.network

import retrofit2.http.GET

/**
 * 钱包API接口
 * 定义获取货币、汇率和钱包余额的网络请求方法
 */
interface WalletApi {
    
    /**
     * 获取支持的货币列表
     */
    @GET("currencies")
    suspend fun getCurrencies(): CurrenciesResponse
    
    /**
     * 获取实时汇率
     */
    @GET("live-rates")
    suspend fun getLiveRates(): LiveRatesResponse
    
    /**
     * 获取钱包余额
     */
    @GET("wallet-balance")
    suspend fun getWalletBalance(): WalletBalanceResponse
} 