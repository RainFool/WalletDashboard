package com.rainfool.wallet.data.network

import com.rainfool.wallet.data.model.Currency
import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.Rate
import com.rainfool.wallet.data.model.WalletBalance
import kotlinx.coroutines.delay

/**
 * 模拟钱包API实现
 * 延迟1秒后返回模拟数据，用于开发和测试
 */
class MockWalletApi : WalletApi {
    
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
        
        return LiveRatesResponse(
            ok = true,
            warning = "",
            tiers = listOf(
                ExchangeRate(
                    fromCurrency = "BTC",
                    toCurrency = "USD",
                    rates = listOf(
                        Rate(amount = "1", rate = "45000.0")
                    ),
                    timeStamp = System.currentTimeMillis()
                ),
                ExchangeRate(
                    fromCurrency = "ETH",
                    toCurrency = "USD",
                    rates = listOf(
                        Rate(amount = "1", rate = "3200.0")
                    ),
                    timeStamp = System.currentTimeMillis()
                ),
                ExchangeRate(
                    fromCurrency = "CRO",
                    toCurrency = "USD",
                    rates = listOf(
                        Rate(amount = "1", rate = "0.08")
                    ),
                    timeStamp = System.currentTimeMillis()
                )
            )
        )
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