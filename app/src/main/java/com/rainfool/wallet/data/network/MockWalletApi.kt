package com.rainfool.wallet.data.network

import com.rainfool.wallet.data.model.Currency
import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.Rate
import com.rainfool.wallet.data.model.WalletBalance
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * 模拟钱包API实现
 * 延迟1秒后返回模拟数据，用于开发和测试
 */
class MockWalletApi : WalletApi {
    
    // 基础汇率数据
    private val baseRates = mapOf(
        "BTC" to 45000.0,
        "ETH" to 3200.0,
        "CRO" to 0.08
    )
    
    // 汇率变化范围（百分比）
    private val rateChangeRange = 0.05 // 5%的变化范围
    
    // 失败概率（10%）
    private val failureRate = 0.1
    
    override suspend fun getCurrencies(): CurrenciesResponse {
        delay(1000) // 模拟网络延迟1秒
        
        return CurrenciesResponse(
            currencies = listOf(
                Currency(
                    coinId = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    tokenDecimal = 8,
                    contractAddress = "",
                    withdrawalEta = listOf("1-3 business days"),
                    colorfulImageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
                    grayImageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
                    hasDepositAddressTag = false,
                    minBalance = 0.001,
                    blockchainSymbol = "BTC",
                    tradingSymbol = "BTC",
                    code = "BTC",
                    explorer = "https://blockchain.info",
                    isErc20 = false,
                    gasLimit = 0,
                    tokenDecimalValue = "100000000",
                    displayDecimal = 8,
                    supportsLegacyAddress = true,
                    depositAddressTagName = "",
                    depositAddressTagType = "",
                    numConfirmationRequired = 2
                ),
                Currency(
                    coinId = "ethereum",
                    name = "Ethereum",
                    symbol = "ETH",
                    tokenDecimal = 18,
                    contractAddress = "",
                    withdrawalEta = listOf("1-3 business days"),
                    colorfulImageUrl = "https://assets.coingecko.com/coins/images/279/large/ethereum.png",
                    grayImageUrl = "https://assets.coingecko.com/coins/images/279/large/ethereum.png",
                    hasDepositAddressTag = false,
                    minBalance = 0.01,
                    blockchainSymbol = "ETH",
                    tradingSymbol = "ETH",
                    code = "ETH",
                    explorer = "https://etherscan.io",
                    isErc20 = false,
                    gasLimit = 21000,
                    tokenDecimalValue = "1000000000000000000",
                    displayDecimal = 18,
                    supportsLegacyAddress = true,
                    depositAddressTagName = "",
                    depositAddressTagType = "",
                    numConfirmationRequired = 12
                ),
                Currency(
                    coinId = "crypto-com-chain",
                    name = "Cronos",
                    symbol = "CRO",
                    tokenDecimal = 8,
                    contractAddress = "",
                    withdrawalEta = listOf("1-3 business days"),
                    colorfulImageUrl = "https://assets.coingecko.com/coins/images/7310/large/cro_token_logo.png",
                    grayImageUrl = "https://assets.coingecko.com/coins/images/7310/large/cro_token_logo.png",
                    hasDepositAddressTag = false,
                    minBalance = 1.0,
                    blockchainSymbol = "CRO",
                    tradingSymbol = "CRO",
                    code = "CRO",
                    explorer = "https://cronos.crypto.org",
                    isErc20 = false,
                    gasLimit = 0,
                    tokenDecimalValue = "100000000",
                    displayDecimal = 8,
                    supportsLegacyAddress = true,
                    depositAddressTagName = "",
                    depositAddressTagType = "",
                    numConfirmationRequired = 1
                )
            ),
            total = 3,
            ok = true
        )
    }
    
    override suspend fun getLiveRates(): LiveRatesResponse {
        delay(1000) // 模拟网络延迟1秒
        
        // 随机失败检查
        if (Random.nextDouble() < failureRate) {
            throw RuntimeException("网络请求失败，请稍后重试")
        }
        
        // 生成随机变化的汇率
        val currentTime = System.currentTimeMillis()
        
        return LiveRatesResponse(
            ok = true,
            warning = "",
            tiers = listOf(
                ExchangeRate(
                    fromCurrency = "BTC",
                    toCurrency = "USD",
                    rates = listOf(
                        Rate(amount = "1", rate = generateRandomRate("BTC", currentTime))
                    ),
                    timeStamp = currentTime
                ),
                ExchangeRate(
                    fromCurrency = "ETH",
                    toCurrency = "USD",
                    rates = listOf(
                        Rate(amount = "1", rate = generateRandomRate("ETH", currentTime))
                    ),
                    timeStamp = currentTime
                ),
                ExchangeRate(
                    fromCurrency = "CRO",
                    toCurrency = "USD",
                    rates = listOf(
                        Rate(amount = "1", rate = generateRandomRate("CRO", currentTime))
                    ),
                    timeStamp = currentTime
                )
            )
        )
    }
    
    /**
     * 生成随机变化的汇率
     * @param currency 货币代码
     * @param timestamp 时间戳，用于确保每秒变化
     * @return 格式化后的汇率字符串
     */
    private fun generateRandomRate(currency: String, timestamp: Long): String {
        val baseRate = baseRates[currency] ?: return "0.0"
        
        // 使用时间戳确保每秒变化
        val seed = timestamp / 1000 // 每秒一个种子
        val random = Random(seed)
        
        // 生成-5%到+5%的随机变化
        val changePercent = (random.nextDouble() - 0.5) * 2 * rateChangeRange
        val newRate = baseRate * (1 + changePercent)
        
        // 根据货币类型格式化汇率
        return when (currency) {
            "BTC" -> "%.2f".format(newRate)
            "ETH" -> "%.2f".format(newRate)
            "CRO" -> "%.4f".format(newRate)
            else -> "%.2f".format(newRate)
        }
    }
    
    override suspend fun getWalletBalance(): WalletBalanceResponse {
        delay(1000) // 模拟网络延迟1秒
        
        return WalletBalanceResponse(
            ok = true,
            warning = "",
            wallet = listOf(
                WalletBalance(
                    currency = "BTC",
                    amount = 0.5
                ),
                WalletBalance(
                    currency = "ETH",
                    amount = 2.5
                ),
                WalletBalance(
                    currency = "CRO",
                    amount = 1000.0
                )
            )
        )
    }
} 