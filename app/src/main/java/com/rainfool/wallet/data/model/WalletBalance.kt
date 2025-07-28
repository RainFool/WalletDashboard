package com.rainfool.wallet.data.model

import com.google.gson.annotations.SerializedName

/**
 * Wallet balance data model
 * Corresponds to balance information in wallet-balance-json.md
 */
data class WalletBalance(
    @SerializedName("currency")
    val currency: String,
    
    @SerializedName("amount")
    val amount: Double
) 