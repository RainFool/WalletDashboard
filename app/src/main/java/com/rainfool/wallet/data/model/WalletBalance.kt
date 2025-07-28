package com.rainfool.wallet.data.model

import com.google.gson.annotations.SerializedName

/**
 * 钱包余额数据模型
 * 对应wallet-balance-json.md中的余额信息
 */
data class WalletBalance(
    @SerializedName("currency")
    val currency: String,
    
    @SerializedName("amount")
    val amount: Double
) 