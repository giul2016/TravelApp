package com.devgiul.mychofer.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devgiul.mychofer.data.dto.RideEstimateRequest
import com.devgiul.mychofer.domain.repository.RideRepository
import com.devgiul.mychofer.model.RideEstimateModel
import com.devgiul.mychofer.utils.ApiException
import com.devgiul.mychofer.utils.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RideViewModel @Inject constructor(
    private val rideRepository: RideRepository
) : ViewModel() {

    private val _rideOptions = MutableLiveData<RideEstimateModel>()
    val rideOptions: LiveData<RideEstimateModel> = _rideOptions

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun estimateRide(customerId: String, origin: String, destination: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val request = RideEstimateRequest(
                    customer_id = customerId,
                    origin = origin,
                    destination = destination
                )
                val response = rideRepository.estimateRide(request)

                // Usando o método estático fromDto para mapear para o modelo
                val model = RideEstimateModel.fromDto(response)
                _rideOptions.postValue(model)
            } catch (e: ApiException) {
                val apiErrorCode = ErrorUtils.ApiErrorCode.fromCode(e.errorCode)
                _errorMessage.postValue(apiErrorCode.userMessage)
            } catch (e: Exception) {
                _errorMessage.postValue(ErrorUtils.ApiErrorCode.UNKNOWN_ERROR.userMessage)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

}
