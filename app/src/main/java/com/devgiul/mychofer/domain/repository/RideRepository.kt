package com.devgiul.mychofer.domain.repository


import com.devgiul.mychofer.data.dto.RideConfirmRequest
import com.devgiul.mychofer.data.dto.RideConfirmResponse
import com.devgiul.mychofer.data.dto.RideEstimateRequest
import com.devgiul.mychofer.data.dto.RideEstimateResponse
import com.devgiul.mychofer.data.dto.RideHistoryResponse

interface RideRepository {
    suspend fun estimateRide(request: RideEstimateRequest): RideEstimateResponse
    suspend fun confirmRide(request: RideConfirmRequest): RideConfirmResponse
    suspend fun getRideHistory(customerId: String, driverId: Int? = null): RideHistoryResponse
}
