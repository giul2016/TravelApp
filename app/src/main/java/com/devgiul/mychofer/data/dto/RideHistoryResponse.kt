package com.devgiul.mychofer.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RideHistoryResponse(
    val customer_id: String,
    val rides: List<Ride>
) : Parcelable

@Parcelize
data class Ride(
    val id: Int,
    val date: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: Driver,
    val value: Double
) : Parcelable

@Parcelize
data class Driver(
    val id: Int,
    val name: String
) : Parcelable

