package com.devgiul.mychofer.model

import android.os.Parcelable
import com.devgiul.mychofer.data.dto.Location
import com.devgiul.mychofer.data.dto.Review
import com.devgiul.mychofer.data.dto.RideEstimateResponse
import com.devgiul.mychofer.data.dto.RideOption
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RideOptionModel(
    val id: Int,
    val name: String,
    val description: String,
    val vehicle: String,
    val review: ReviewModel,
    val value: Double
) : Parcelable {
    companion object {
        fun fromDto(dto: RideOption): RideOptionModel {
            return RideOptionModel(
                id = dto.id,
                name = dto.name,
                description = dto.description,
                vehicle = dto.vehicle,
                review = ReviewModel.fromDto(dto.review),
                value = dto.value
            )
        }
    }
}

@Parcelize
data class ReviewModel(
    val rating: Double,
    val comment: String
) : Parcelable {
    companion object {
        fun fromDto(dto: Review): ReviewModel {
            return ReviewModel(
                rating = dto.rating,
                comment = dto.comment
            )
        }
    }
}

@Parcelize
data class LocationModel(
    val latitude: Double,
    val longitude: Double,
    val address: String?
) : Parcelable {
    companion object {
        fun fromDto(dto: Location): LocationModel {
            return LocationModel(
                latitude = dto.latitude,
                longitude = dto.longitude,
                address = dto.address
            )
        }
    }
}

@Parcelize
data class RideEstimateModel(
    val origin: LocationModel,
    val destination: LocationModel,
    val distance: Double,
    val duration: String,
    val options: List<RideOptionModel>
) : Parcelable {
    companion object {
        fun fromDto(dto: RideEstimateResponse): RideEstimateModel {
            return RideEstimateModel(
                origin = LocationModel.fromDto(dto.origin),
                destination = LocationModel.fromDto(dto.destination),
                distance = dto.distance,
                duration = dto.duration,
                options = dto.options.map { RideOptionModel.fromDto(it) }
            )
        }
    }
}

