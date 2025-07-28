package com.rainfool.wallet.data.network

import com.google.gson.annotations.SerializedName
import com.rainfool.wallet.data.model.Currency
import com.rainfool.wallet.data.model.ExchangeRate
import com.rainfool.wallet.data.model.WalletBalance

/**
 * 货币列表API响应
 */
data class CurrenciesResponse(
    @SerializedName("currencies")
    val currencies: List<Currency>,

    @SerializedName("total")
    val total: Int,

    @SerializedName("ok")
    val ok: Boolean
)

/**
 * 汇率API响应
 */
data class LiveRatesResponse(
    @SerializedName("ok")
    val ok: Boolean,
    
    @SerializedName("warning")
    val warning: String,
    
    @SerializedName("tiers")
    val tiers: List<ExchangeRate>
)

/**
 * 钱包余额API响应
 */
data class WalletBalanceResponse(
    @SerializedName("ok")
    val ok: Boolean,
    
    @SerializedName("warning")
    val warning: String,
    
    @SerializedName("wallet")
    val wallet: List<WalletBalance>
)

/**
 * 通用API响应包装
 */
sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val exception: Exception? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
} 