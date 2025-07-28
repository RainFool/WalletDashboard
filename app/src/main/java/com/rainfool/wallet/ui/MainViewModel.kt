package com.rainfool.wallet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainfool.wallet.data.model.WalletBalance
import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.Currency
import com.rainfool.wallet.data.repository.WalletRepository
import com.rainfool.wallet.di.DependencyProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class MainViewModel : ViewModel() {
    
    private val repository: WalletRepository = DependencyProvider.provideWalletRepository()
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    init {
        loadInitialData()
        startLiveRateUpdates()
    }
    
    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                // Load currency information
                val currenciesResult = repository.getCurrencies()
                currenciesResult.fold(
                    onSuccess = { currencies ->
                        // Load wallet data
                        repository.getWalletBalances().collect { balancesResult ->
                            balancesResult.fold(
                                onSuccess = { balances ->
                                    // Load exchange rate data
                                    repository.getExchangeRates().collect { ratesResult ->
                                        ratesResult.fold(
                                            onSuccess = { rates ->
                                                val totalUsdValue = calculateTotalUsdValue(balances, rates)
                                                _uiState.value = _uiState.value.copy(
                                                    isLoading = false,
                                                    currencies = currencies,
                                                    walletBalances = balances,
                                                    exchangeRates = rates,
                                                    totalUsdValue = totalUsdValue,
                                                    message = "Wallet data loaded successfully, total value: $${String.format("%.2f", totalUsdValue)}"
                                                )
                                            },
                                            onFailure = { error ->
                                                _uiState.value = _uiState.value.copy(
                                                    isLoading = false,
                                                    currencies = currencies,
                                                    walletBalances = balances,
                                                    message = "Failed to load exchange rate data: ${error.message}"
                                                )
                                            }
                                        )
                                    }
                                },
                                onFailure = { error ->
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        currencies = currencies,
                                        message = "Failed to load wallet data: ${error.message}"
                                    )
                                }
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            message = "Failed to load currency information: ${error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Initialization failed: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Start periodic exchange rate updates
     */
    private fun startLiveRateUpdates() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    delay(1000) // Update every second
                    
                    // Get latest exchange rate data
                    repository.getExchangeRates().collect { result ->
                        result.fold(
                            onSuccess = { rates ->
                                val currentState = _uiState.value
                                val totalUsdValue = calculateTotalUsdValue(currentState.walletBalances, rates)
                                
                                _uiState.value = currentState.copy(
                                    exchangeRates = rates,
                                    totalUsdValue = totalUsdValue,
                                    message = "Exchange rates updated, total value: $${String.format("%.2f", totalUsdValue)}"
                                )
                            },
                            onFailure = { error ->
                                val currentState = _uiState.value
                                _uiState.value = currentState.copy(
                                    message = "Failed to update exchange rates: ${error.message}"
                                )
                            }
                        )
                    }
                } catch (e: Exception) {
                    val currentState = _uiState.value
                    _uiState.value = currentState.copy(
                        message = "Exchange rate update exception: ${e.message}"
                    )
                }
            }
        }
    }
    
    private fun calculateTotalUsdValue(balances: List<WalletBalance>, rates: List<ExchangeRate>): Double {
        var totalUsdValue = 0.0
        
        balances.forEach { balance ->
            val rate = rates.find { it.fromCurrency == balance.currency && it.toCurrency == "USD" }
            if (rate != null && rate.rates.isNotEmpty()) {
                val usdRate = rate.rates.first().rate.toDoubleOrNull() ?: 0.0
                val usdValue = balance.amount * usdRate
                totalUsdValue += usdValue
            }
        }
        
        return totalUsdValue
    }
    
    fun updateMessage(newMessage: String) {
        _uiState.value = _uiState.value.copy(message = newMessage)
    }
}

data class MainUiState(
    val isLoading: Boolean = true,
    val message: String = "",
    val currencies: List<Currency> = emptyList(),
    val walletBalances: List<WalletBalance> = emptyList(),
    val exchangeRates: List<ExchangeRate> = emptyList(),
    val totalUsdValue: Double = 0.0
) 