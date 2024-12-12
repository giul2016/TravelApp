package com.devgiul.mychofer.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RideConfirmRequest(
    val customer_id: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: DriverDetails,
    val value: Double
) : Parcelable

@Parcelize
data class DriverDetails(
    val id: Int,
    val name: String
): Parcelable

