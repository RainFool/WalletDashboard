package com.rainfool.wallet.di

import com.rainfool.wallet.data.network.MockWalletApi
import com.rainfool.wallet.data.network.WalletApi
import com.rainfool.wallet.data.repository.WalletRepository

/**
 * Dependency injection provider
 * Provides all dependency instances required by the application
 */
object DependencyProvider {
    
    /**
     * Provide WalletApi instance
     * Uses mock implementation, returns data after 1 second delay
     */
    fun provideWalletApi(): WalletApi {
        return MockWalletApi()
    }
    
    /**
     * Provide WalletRepository instance
     */
    fun provideWalletRepository(): WalletRepository {
        return WalletRepository(provideWalletApi())
    }
} 