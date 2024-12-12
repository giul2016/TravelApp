package com.devgiul.mychofer.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devgiul.mychofer.domain.repository.RideRepository
import com.devgiul.mychofer.model.RideConfirmModel
import com.devgiul.mychofer.model.RideConfirmResult
import com.devgiul.mychofer.presentation.usecase.ValidateRideUseCase
import com.devgiul.mychofer.utils.ApiException
import com.devgiul.mychofer.utils.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.devgiul.mychofer.utils.Result

@HiltViewModel
class RideOptionsViewModel @Inject constructor(
    private val repository: RideRepository,
    private val validateRideUseCase: ValidateRideUseCase
) : ViewModel() {

    private val _confirmRideState = MutableLiveData<Result<RideConfirmResult>>()
    val confirmRideState: LiveData<Result<RideConfirmResult>> get() = _confirmRideState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun confirmRide(request: RideConfirmModel) {
        viewModelScope.launch {
            // Primeiro, valida a requisição
            when (val validationResult = validateRideUseCase.execute(request)) {
                is Result.Failure -> {
                    _errorMessage.value = validationResult.exception.message
                    return@launch // Sai da corrotina sem continuar a execução
                }

                is Result.Success -> {
                    // Continuação da lógica de confirmação de viagem
                    _confirmRideState.value = Result.loading()
                    try {
                        val dtoRequest = RideConfirmModel.toDto(request)
                        val response = repository.confirmRide(dtoRequest)

                        val result = RideConfirmResult.fromDto(response)
                        if (result.isSuccess) {
                            _confirmRideState.value = Result.success(result)
                        } else {
                            _confirmRideState.value = Result.failure(Exception(result.message))
                        }
                    } catch (e: ApiException) {
                        val apiErrorCode = ErrorUtils.ApiErrorCode.fromCode(e.errorCode)
                        _errorMessage.postValue(apiErrorCode.userMessage)
                    } catch (e: Exception) {
                        _errorMessage.postValue(ErrorUtils.ApiErrorCode.UNKNOWN_ERROR.userMessage)
                    }
                }

                is Result.Loading -> {
                    // O que fazer quando o estado é "Loading"
                    _confirmRideState.value = Result.loading() // Caso queira manter o loading
                }

                else -> {
                    // Trata outros casos, caso haja algum
                    _errorMessage.value = "Erro desconhecido"
                }
            }
        }
    }
}

