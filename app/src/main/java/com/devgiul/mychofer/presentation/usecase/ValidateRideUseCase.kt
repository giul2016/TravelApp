package com.devgiul.mychofer.presentation.usecase

import com.devgiul.mychofer.domain.repository.RideRepository
import com.devgiul.mychofer.model.RideConfirmModel
import com.devgiul.mychofer.utils.Result
import javax.inject.Inject

class ValidateRideUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {

    private val motoristaDistanciaMinima = mapOf(
        1 to 1,   // Motorista ID 1 aceita no mínimo 1 km
        2 to 5,   // Motorista ID 2 aceita no mínimo 5 km
        3 to 10   // Motorista ID 3 aceita no mínimo 10 km
    )

    fun execute(request: RideConfirmModel): Result<Unit> {
        val motoristaId = request.driver.id
        val distancia = request.distance

        // Verificando se o motorista é válido com base no repositório
        if (!motoristaDistanciaMinima.containsKey(motoristaId)) {
            return Result.Failure(Exception("Motorista inválido. Por favor, selecione um motorista válido."))
        }

        val distanciaMinima = motoristaDistanciaMinima[motoristaId] ?: 0
        if (distancia < distanciaMinima) {
            return Result.Failure(Exception("Distância inválida para o motorista selecionado. Mínimo aceito: $distanciaMinima km."))
        }

        // Verificando se a origem e o destino são iguais
        if (request.origin == request.destination) {
            return Result.Failure(Exception("Erro: O endereço de origem não pode ser igual ao destino."))
        }

        return Result.Success(Unit) // Tudo validado
    }
}
