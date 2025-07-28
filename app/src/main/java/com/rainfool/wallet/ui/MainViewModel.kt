package com.rainfool.wallet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainfool.wallet.data.model.WalletBalance
import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.Currency
import com.rainfool.wallet.data.repository.WalletRepository
import com.rainfool.wallet.di.DependencyProvider
import com.rainfool.wallet.data.util.ExchangeRateCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.CancellationException

/**
 * MainViewModel manages the wallet dashboard UI state and data operations
 * Handles currency loading, wallet balances, exchange rates, and total USD value calculation
 */
class MainViewModel : ViewModel() {

    // Repository for data operations
    private val repository: WalletRepository = DependencyProvider.provideWalletRepository()

    // UI state management
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    // Data flow control
    private var dataFlowJob: Job? = null
    private var isDataFlowActive = false
    private var isCurrencyLoaded = false

    init {
        loadInitialData()
        startDataFlow()
    }

    /**
     * Start data flow when app becomes visible
     */
    fun onAppVisible() {
        // Retry currency loading if it failed previously
        if (!isCurrencyLoaded) {
            loadInitialData()
        }
        
        if (!isDataFlowActive) {
            startDataFlow()
        }
    }

    /**
     * Stop data flow when app becomes invisible
     */
    fun onAppInvisible() {
        stopDataFlow()
    }

    /**
     * Stop the current data flow
     */
    private fun stopDataFlow() {
        dataFlowJob?.cancel()
        dataFlowJob = null
        isDataFlowActive = false
        _uiState.value = _uiState.value.copy(
            message = "Data flow stopped"
        )
    }

    /**
     * Load initial currency data with error handling
     */
    private fun loadInitialData() = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Load currency information with retry mechanism
            val currenciesResult = getCurrenciesWithRetry()
            currenciesResult.fold(
                onSuccess = { currencies ->
                    _uiState.value = _uiState.value.copy(
                        currencies = currencies,
                        message = "Currency information loaded successfully"
                    )
                    isCurrencyLoaded = true
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Failed to load currency information after retries: ${error.message}"
                    )
                    isCurrencyLoaded = false
                }
            )

        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                message = "Initialization failed: ${e.message}"
            )
            isCurrencyLoaded = false
        }
    }

    /**
     * Get currencies with retry mechanism
     * Uses exponential backoff strategy with max 3 retries
     */
    private suspend fun getCurrenciesWithRetry(): Result<List<Currency>> {
        val maxRetries = 3
        var lastException: Exception? = null

        for (attempt in 0..maxRetries) {
            try {
                val result = repository.getCurrencies()
                if (result.isSuccess) {
                    return result
                } else {
                    lastException = result.exceptionOrNull() as? Exception ?: Exception("Unknown error")
                }
            } catch (e: Exception) {
                lastException = e
            }

            // If this is not the last attempt, wait before retrying
            if (attempt < maxRetries) {
                val delayMs = (1000L * (1 shl attempt)).coerceAtMost(5000L) // Exponential backoff, max 5 seconds
                delay(delayMs)
            }
        }

        return Result.failure(lastException ?: Exception("Failed after $maxRetries retries"))
    }

    /**
     * Start continuous data flow monitoring
     * Combines wallet balances and exchange rates for real-time updates
     */
    private fun startDataFlow() {
        // Cancel existing data flow if any
        dataFlowJob?.cancel()
        
        dataFlowJob = viewModelScope.launch {
            try {
                isDataFlowActive = true
                _uiState.value = _uiState.value.copy(
                    message = "Data flow started"
                )
                
                // Combine wallet balances and exchange rates flows
                combine(
                    repository.getWalletBalances(),
                    repository.getExchangeRates()
                ) { balancesResult, ratesResult ->
                    // Process wallet balances
                    val balances = balancesResult.getOrNull() ?: emptyList()
                    val balanceError = balancesResult.exceptionOrNull()

                    // Process exchange rates
                    val rates = ratesResult.getOrNull() ?: emptyList()
                    val ratesError = ratesResult.exceptionOrNull()

                    // Calculate total USD value using utility class
                    val totalUsdValue = ExchangeRateCalculator.calculateTotalUsdValue(balances, rates)

                    // Update UI state
                    val currentState = _uiState.value
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        walletBalances = balances,
                        exchangeRates = rates,
                        totalUsdValue = totalUsdValue,
                        message = when {
                            balanceError != null -> "Failed to load wallet data: ${balanceError.message}"
                            ratesError != null -> "Failed to load exchange rates: ${ratesError.message}"
                            balances.isNotEmpty() && rates.isNotEmpty() ->
                                "Data updated, total value: ${ExchangeRateCalculator.formatUsdValue(totalUsdValue)}"

                            else -> "Waiting for data..."
                        }
                    )
                }.catch { error ->
                    val currentState = _uiState.value
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        message = "Data flow error: ${error.message}"
                    )
                }.collect {
                    // do nothing for now,just for starting the flow
                }
            } catch (e: Exception) {
                // Handle cancellation and other exceptions
                if (e is kotlinx.coroutines.CancellationException) {
                    // Normal cancellation, don't update UI
                    return@launch
                }
                
                val currentState = _uiState.value
                _uiState.value = currentState.copy(
                    isLoading = false,
                    message = "Data flow error: ${e.message}"
                )
            } finally {
                // Ensure state is updated when flow is cancelled
                if (!isDataFlowActive) {
                    _uiState.value = _uiState.value.copy(
                        message = "Data flow stopped"
                    )
                }
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        stopDataFlow()
    }

}

/**
 * UI state data class for the wallet dashboard
 * Contains loading state, messages, currencies, balances, rates, and total USD value
 */
data class MainUiState(
    val isLoading: Boolean = true,
    val message: String = "",
    val currencies: List<Currency> = emptyList(),
    val walletBalances: List<WalletBalance> = emptyList(),
    val exchangeRates: List<ExchangeRate> = emptyList(),
    val totalUsdValue: Double = 0.0
) 