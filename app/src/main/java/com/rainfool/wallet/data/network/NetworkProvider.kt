package com.rainfool.wallet.data.network

/**
 * 网络依赖提供类
 * 提供网络相关的依赖实例
 */
object NetworkProvider {
    
    /**
     * 提供WalletApi实例
     * 使用模拟实现，延迟1秒返回数据
     */
    fun provideWalletApi(): WalletApi {
        return MockWalletApi()
    }
    
    /**
     * 提供NetworkService实例
     */
    fun provideNetworkService(): NetworkService {
        return NetworkService(provideWalletApi())
    }
} 