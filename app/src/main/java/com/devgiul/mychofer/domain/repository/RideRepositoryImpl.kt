package com.devgiul.mychofer.domain.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.devgiul.mychofer.data.api.RideApi
import com.devgiul.mychofer.data.dto.RideConfirmRequest
import com.devgiul.mychofer.data.dto.RideConfirmResponse
import com.devgiul.mychofer.data.dto.RideEstimateRequest
import com.devgiul.mychofer.data.dto.RideEstimateResponse
import com.devgiul.mychofer.data.dto.RideHistoryResponse
import com.devgiul.mychofer.utils.ApiException
import com.devgiul.mychofer.utils.ErrorUtils
import com.devgiul.mychofer.utils.parseErrorCode
import javax.inject.Inject


class RideRepositoryImpl @Inject constructor(
    private val rideApi: RideApi,
    private val context: Context
) : RideRepository {

    override suspend fun estimateRide(request: RideEstimateRequest): RideEstimateResponse {
        if (!isNetworkAvailable()) {
            throw Exception("Sem conexão com a Internet")
        }

        Log.d("API_REQUEST", "Iniciando requisição para /ride/estimate com dados: $request")

        val response = rideApi.estimateRide(request)

        if (response.isSuccessful) {

            Log.d("API_RESPONSE_SUCCESS", "Resposta recebida com sucesso: ${response.body()}")
            return response.body() ?: throw ApiException(
                ErrorUtils.ApiErrorCode.UNKNOWN_ERROR.code,
                "Resposta vazia"
            )
        } else {
            val errorBody = response.errorBody()?.string() ?: ""
            val errorCode = parseErrorCode(errorBody)

            val apiError = ErrorUtils.ApiErrorCode.fromCode(errorCode)
            Log.e("API_RESPONSE_ERROR", "Erro na resposta: $apiError - $errorBody")

            // Lança a exceção com a mensagem associada ao erro
            throw ApiException(apiError.code, apiError.userMessage)
        }
    }


    override suspend fun confirmRide(request: RideConfirmRequest): RideConfirmResponse {
        if (!isNetworkAvailable()) {
            throw Exception("Sem conexão com a Internet")
        }

        Log.d("API_REQUEST", "Iniciando requisição para /ride/confirm com dados: $request")
        val response = rideApi.confirmRide(request)

        if (response.isSuccessful) {

            Log.d("API_RESPONSE_SUCCESS", "Resposta recebida com sucesso: ${response.body()}")
            return response.body() ?: throw ApiException(
                ErrorUtils.ApiErrorCode.UNKNOWN_ERROR.code,
                "Resposta vazia"
            )
        } else {

            val errorBody = response.errorBody()?.string() ?: ""
            val errorCode = parseErrorCode(errorBody)

            val apiError = ErrorUtils.ApiErrorCode.fromCode(errorCode)
            Log.e("API_RESPONSE_ERROR", "Erro na resposta: $apiError - $errorBody")

            throw ApiException(apiError.code, apiError.userMessage)
        }
    }

    override suspend fun getRideHistory(customerId: String, driverId: Int?): RideHistoryResponse {
        if (!isNetworkAvailable()) {
            throw Exception("Sem conexão com a Internet")
        }

        val response = rideApi.getRideHistory(customerId, driverId)
        Log.d("API_REQUEST", "Iniciando requisição para /ride/$customerId com driverId=$driverId")

        if (response.isSuccessful) {
            Log.d("API_RESPONSE_SUCCESS", "Resposta recebida com sucesso: ${response.body()}")
            // Verifica se o corpo da resposta não é nulo, se for, lança uma exceção personalizada
            return response.body() ?: throw ApiException(
                ErrorUtils.ApiErrorCode.UNKNOWN_ERROR.code,
                "Resposta vazia"
            )
        } else {
            // Extrai o corpo do erro, se houver, para um diagnóstico mais detalhado
            val errorBody = response.errorBody()?.string() ?: ""
            val errorCode = parseErrorCode(errorBody)

            // Utiliza um código de erro mais específico baseado no erro retornado pela API
            val apiError = ErrorUtils.ApiErrorCode.fromCode(errorCode)

            Log.e("API_RESPONSE_ERROR", "Erro na resposta: $apiError")

            // Lança a exceção com código e mensagem mais informativa
            throw ApiException(apiError.code, apiError.userMessage)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

