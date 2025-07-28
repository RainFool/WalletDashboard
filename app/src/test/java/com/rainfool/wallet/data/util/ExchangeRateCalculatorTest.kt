package com.rainfool.wallet.data.util

import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.Rate
import com.rainfool.wallet.data.model.WalletBalance
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeRateCalculatorTest {
    
    @Test
    fun `calculateUsdValue should return correct USD value for valid rate`() {
        // Given
        val balance = WalletBalance("BTC", 1.5)
        val exchangeRates = listOf(
            ExchangeRate("BTC", "USD", listOf(Rate("1", "50000.00")), System.currentTimeMillis())
        )
        
        // When
        val result = ExchangeRateCalculator.calculateUsdValue(balance, exchangeRates)
        
        // Then
        assertEquals(75000.0, result, 0.01)
    }
    
    @Test
    fun `calculateUsdValue should return zero for missing rate`() {
        // Given
        val balance = WalletBalance("BTC", 1.5)
        val exchangeRates = emptyList<ExchangeRate>()
        
        // When
        val result = ExchangeRateCalculator.calculateUsdValue(balance, exchangeRates)
        
        // Then
        assertEquals(0.0, result, 0.01)
    }
    
    @Test
    fun `calculateTotalUsdValue should return sum of all balances`() {
        // Given
        val balances = listOf(
            WalletBalance("BTC", 1.0),
            WalletBalance("ETH", 10.0)
        )
        val exchangeRates = listOf(
            ExchangeRate("BTC", "USD", listOf(Rate("1", "50000.00")), System.currentTimeMillis()),
            ExchangeRate("ETH", "USD", listOf(Rate("1", "3000.00")), System.currentTimeMillis())
        )
        
        // When
        val result = ExchangeRateCalculator.calculateTotalUsdValue(balances, exchangeRates)
        
        // Then
        assertEquals(80000.0, result, 0.01)
    }
    
    @Test
    fun `findUsdRate should return correct rate for valid currency`() {
        // Given
        val exchangeRates = listOf(
            ExchangeRate("BTC", "USD", listOf(Rate("1", "50000.00")), System.currentTimeMillis())
        )
        
        // When
        val result = ExchangeRateCalculator.findUsdRate("BTC", exchangeRates)?: 0.0
        
        // Then
        assertEquals(50000.0, result, 0.01)
    }
    
    @Test
    fun `findUsdRate should return null for missing currency`() {
        // Given
        val exchangeRates = listOf(
            ExchangeRate("BTC", "USD", listOf(Rate("1", "50000.00")), System.currentTimeMillis())
        )
        
        // When
        val result = ExchangeRateCalculator.findUsdRate("ETH", exchangeRates)
        
        // Then
        assertEquals(null, result)
    }
    
    @Test
    fun `formatUsdValue should format correctly`() {
        // Given
        val usdValue = 1234.567
        
        // When
        val result = ExchangeRateCalculator.formatUsdValue(usdValue)
        
        // Then
        assertEquals("$1234.57", result)
    }
    
    @Test
    fun `isSupportedCurrency should return true for supported currencies`() {
        // Given & When & Then
        assertEquals(true, ExchangeRateCalculator.isSupportedCurrency("BTC"))
        assertEquals(true, ExchangeRateCalculator.isSupportedCurrency("ETH"))
        assertEquals(true, ExchangeRateCalculator.isSupportedCurrency("CRO"))
    }
    
    @Test
    fun `isSupportedCurrency should return false for unsupported currencies`() {
        // Given & When & Then
        assertEquals(false, ExchangeRateCalculator.isSupportedCurrency("USD"))
        assertEquals(false, ExchangeRateCalculator.isSupportedCurrency("EUR"))
    }
} 