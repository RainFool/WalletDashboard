package com.rainfool.wallet.di

import com.rainfool.wallet.data.network.MockWalletApi
import com.rainfool.wallet.data.network.WalletApi
import com.rainfool.wallet.data.repository.WalletRepository

/**
 * 依赖注入提供者
 * 提供应用所需的所有依赖实例
 */
object DependencyProvider {
    
    /**
     * 提供WalletApi实例
     * 使用模拟实现，延迟1秒返回数据
     */
    fun provideWalletApi(): WalletApi {
        return MockWalletApi()
    }
    
    /**
     * 提供WalletRepository实例
     */
    fun provideWalletRepository(): WalletRepository {
        return WalletRepository(provideWalletApi())
    }
} 