package com.devgiul.mychofer.model

import android.os.Parcelable
import com.devgiul.mychofer.data.dto.DriverDetails
import com.devgiul.mychofer.data.dto.RideConfirmRequest
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RideConfirmModel(
    val customerId: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: DriverModel,
    val value: Double
) : Parcelable {
    companion object {
        fun toDto(model: RideConfirmModel): RideConfirmRequest {
            return RideConfirmRequest(
                customer_id = model.customerId,
                origin = model.origin,
                destination = model.destination,
                distance = model.distance,
                duration = model.duration,
                driver = DriverModel.toDto(model.driver),
                value = model.value
            )
        }
    }
}

@Parcelize
data class DriverModel(
    val id: Int,
    val name: String
) : Parcelable {
    companion object {
        fun fromDto(dto: DriverDetails): DriverModel {
            return DriverModel(
                id = dto.id,
                name = dto.name
            )
        }

        fun toDto(model: DriverModel): DriverDetails {
            return DriverDetails(
                id = model.id,
                name = model.name
            )
        }
    }
}
