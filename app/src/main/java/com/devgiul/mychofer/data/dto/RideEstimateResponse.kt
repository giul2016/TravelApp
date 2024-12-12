package com.devgiul.mychofer.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RideEstimateResponse(
    val origin: Location,
    val destination: Location,
    val distance: Double,
    val duration: String,
    val options: List<RideOption>,
    var routeResponse: RouteResponse
) : Parcelable

@Parcelize
data class Location(
    var latitude: Double,
    var longitude: Double,
    val address: String? = null
) : Parcelable



@Parcelize
data class RideOption(
    val id: Int,
    val name: String,
    val description: String,
    val vehicle: String,
    var review: Review,
    val value: Double

) : Parcelable

@Parcelize
data class RouteResponse(
    val distance: String = "",
    val duration: String = "",
    val overviewPolyline: String = ""
): Parcelable

@Parcelize
data class Review(
    val rating: Double,
    val comment: String
): Parcelable
