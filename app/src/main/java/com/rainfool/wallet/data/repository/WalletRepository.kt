package com.rainfool.wallet.data.repository

import com.rainfool.wallet.data.model.Currency
import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.WalletBalance
import com.rainfool.wallet.data.network.WalletApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 钱包数据仓库
 * 负责管理所有数据操作，包括网络请求和本地缓存
 */
class WalletRepository(
    private val walletApi: WalletApi
) {
    
    /**
     * 获取货币列表
     * 货币列表相对静态，只需要请求一次
     */
    suspend fun getCurrencies(): Result<List<Currency>> {
        return try {
            val response = walletApi.getCurrencies()
            if (response.ok) {
                Result.success(response.currencies)
            } else {
                Result.failure(Exception("获取货币列表失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * 获取实时汇率
     */
    fun getExchangeRates(): Flow<Result<List<ExchangeRate>>> = flow {
        try {
            val response = walletApi.getLiveRates()
            if (response.ok) {
                emit(Result.success(response.tiers))
            } else {
                emit(Result.failure(Exception("获取汇率失败")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    /**
     * 获取钱包余额
     */
    fun getWalletBalances(): Flow<Result<List<WalletBalance>>> = flow {
        try {
            val response = walletApi.getWalletBalance()
            if (response.ok) {
                emit(Result.success(response.wallet))
            } else {
                emit(Result.failure(Exception("获取钱包余额失败")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
} 