package com.devgiul.mychofer.data.api


import com.devgiul.mychofer.data.dto.RideConfirmRequest
import com.devgiul.mychofer.data.dto.RideConfirmResponse
import com.devgiul.mychofer.data.dto.RideEstimateRequest
import com.devgiul.mychofer.data.dto.RideEstimateResponse
import com.devgiul.mychofer.data.dto.RideHistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RideApi {
    @POST("/ride/estimate")
    suspend fun estimateRide(@Body request: RideEstimateRequest): Response<RideEstimateResponse>

    @PATCH("/ride/confirm")
    suspend fun confirmRide(
        @Body request: RideConfirmRequest
    ): Response<RideConfirmResponse>

    @GET("/ride/{customer_id}")
    suspend fun getRideHistory(
        @Path("customer_id") customerId: String,
        @Query("driver_id") driverId: Int? = null
    ): Response<RideHistoryResponse>
}
