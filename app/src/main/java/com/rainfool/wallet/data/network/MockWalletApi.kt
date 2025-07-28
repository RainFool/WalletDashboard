package com.rainfool.wallet.data.network

import com.rainfool.wallet.data.model.Currency
import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.Rate
import com.rainfool.wallet.data.model.WalletBalance
import com.rainfool.wallet.data.model.WalletConstants
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Mock wallet API implementation
 * Returns mock data after 1 second delay, used for development and testing
 */
class MockWalletApi : WalletApi {
    
    // Base exchange rate data
    private val baseRates = mapOf(
        "BTC" to 45000.0,
        "ETH" to 3200.0,
        "CRO" to 0.08
    )
    
    // Exchange rate change range (percentage)
    private val rateChangeRange = 0.05 // 5% change range
    
    // Failure probability (10%)
    private val failureRate = 0.1
    
    override suspend fun getCurrencies(): CurrenciesResponse {
        delay(1000) // Simulate network delay of 1 second
        
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
        delay(1000) // Simulate network delay of 1 second
        
        // Random failure check
        if (Random.nextDouble() < failureRate) {
            throw RuntimeException("Network request failed, please try again later")
        }
        
        // Generate randomly changing exchange rates
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
     * Generate randomly changing exchange rate
     * @param currency Currency code
     * @param timestamp Timestamp, used to ensure changes every second
     * @return Formatted exchange rate string
     */
    private fun generateRandomRate(currency: String, timestamp: Long): String {
        val baseRate = baseRates[currency] ?: return "0.0"
        
        // Use timestamp to ensure changes every second
        val seed = timestamp / 1000 // One seed per second
        val random = Random(seed)
        
        // Generate random change from -5% to +5%
        val changePercent = (random.nextDouble() - 0.5) * 2 * rateChangeRange
        val newRate = baseRate * (1 + changePercent)
        
        // Format exchange rate based on currency type using unified constants
        return WalletConstants.formatCurrencyValue(newRate, currency)
    }
    
    override suspend fun getWalletBalance(): WalletBalanceResponse {
        delay(1000) // Simulate network delay of 1 second
        
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