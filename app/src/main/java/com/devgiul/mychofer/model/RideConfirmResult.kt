package com.devgiul.mychofer.model

import com.devgiul.mychofer.data.dto.RideConfirmResponse

data class RideConfirmResult(
    val isSuccess: Boolean,
    val message: String
) {
    companion object {
        fun fromDto(dto: RideConfirmResponse): RideConfirmResult {
            return RideConfirmResult(
                isSuccess = dto.success,
                message = if (dto.success) "Viagem confirmada com sucesso!" else "Confirmação falhou"
            )
        }
    }
}
