package com.rainfool.wallet.data.model

import com.google.gson.annotations.SerializedName

/**
 * 汇率数据模型
 * 对应live-rates-json.md中的汇率信息
 */
data class ExchangeRate(
    @SerializedName("from_currency")
    val fromCurrency: String,
    
    @SerializedName("to_currency")
    val toCurrency: String,
    
    @SerializedName("rates")
    val rates: List<Rate>,
    
    @SerializedName("time_stamp")
    val timeStamp: Long
)

/**
 * 汇率详情数据模型
 */
data class Rate(
    @SerializedName("amount")
    val amount: String,
    
    @SerializedName("rate")
    val rate: String
) 