package com.rainfool.wallet.data.model

import com.google.gson.annotations.SerializedName

/**
 * Exchange rate data model
 * Corresponds to exchange rate information in live-rates-json.md
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
 * Exchange rate detail data model
 */
data class Rate(
    @SerializedName("amount")
    val amount: String,
    
    @SerializedName("rate")
    val rate: String
) 