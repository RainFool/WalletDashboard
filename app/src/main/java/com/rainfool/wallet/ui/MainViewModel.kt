package com.rainfool.wallet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rainfool.wallet.data.repository.WalletRepository
import com.rainfool.wallet.di.DependencyProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    
    private val repository: WalletRepository = DependencyProvider.provideWalletRepository()
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    init {
        loadInitialData()
    }
    
    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                // 加载钱包数据
                repository.getWalletBalances().collect { result ->
                    result.fold(
                        onSuccess = { balances ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                message = "钱包数据加载成功，共${balances.size}个账户"
                            )
                        },
                        onFailure = { error ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                message = "数据加载失败: ${error.message}"
                            )
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "初始化失败: ${e.message}"
                )
            }
        }
    }
    
    fun updateMessage(newMessage: String) {
        _uiState.value = _uiState.value.copy(message = newMessage)
    }
}

data class MainUiState(
    val isLoading: Boolean = true,
    val message: String = ""
) 