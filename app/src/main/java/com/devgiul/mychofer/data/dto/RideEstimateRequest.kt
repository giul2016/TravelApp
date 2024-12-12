package com.devgiul.mychofer.data.dto

data class RideEstimateRequest(
    val customer_id: String,
    val origin: String,
    val destination: String
)

