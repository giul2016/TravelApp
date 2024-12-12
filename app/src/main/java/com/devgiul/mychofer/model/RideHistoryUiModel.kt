package com.devgiul.mychofer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//data class RideHistoryUiModel(
//    val rideId: Int,
//    val driverName: String,
//    val date: String,
//    val value: Double
//)
@Parcelize
data class RideHistoryUiModel(
    val rideId: Int,
    val driverName: String,
    val date: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val value: Double
): Parcelable
