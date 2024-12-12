package com.devgiul.mychofer.utils

class ErrorUtils {
    enum class ApiErrorCode(val code: String, val userMessage: String) {
        INVALID_DATA("INVALID_DATA", "Mesmo endereço de origem e destino"),
        DRIVER_NOT_FOUND("DRIVER_NOT_FOUND", "Motorista não encontrado. Tente novamente."),
        INVALID_DISTANCE("INVALID_DISTANCE", "A distância informada não é válida para o motorista selecionado."),
        NO_RIDES_FOUND("NO_RIDES_FOUND", "Nenhuma viagem encontrada para este cliente.coloque um cliente válido!"),
        INVALID_DRIVER("INVALID_DRIVER", "Motorista inválido. Verifique se o motorista está correto e tente novamente."),
        UNKNOWN_ERROR("UNKNOWN_ERROR", "Ocorreu um erro inesperado. Por favor, tente novamente.");

        companion object {
            fun fromCode(code: String): ApiErrorCode {
                return values().find { it.code == code } ?: UNKNOWN_ERROR
            }
        }
    }
}

