package com.devgiul.mychofer.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devgiul.mychofer.data.dto.Driver
import com.devgiul.mychofer.domain.repository.RideRepository
import com.devgiul.mychofer.model.RideHistoryUiModel
import com.devgiul.mychofer.utils.ApiException
import com.devgiul.mychofer.utils.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.devgiul.mychofer.utils.Result

@HiltViewModel
class RideHistoryViewModel @Inject constructor(
    private val repository: RideRepository
) : ViewModel() {

    private val _rideHistoryState = MutableLiveData<Result<List<RideHistoryUiModel>>>()
    val rideHistoryState: LiveData<Result<List<RideHistoryUiModel>>> get() = _rideHistoryState

    private val _driversState = MutableLiveData<Result<List<Driver>>>()
    val driversState: LiveData<Result<List<Driver>>> get() = _driversState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getRideHistory(customerId: String, driverId: Int? = null) {
        viewModelScope.launch {
            _rideHistoryState.value = Result.loading()
            try {
                val response = repository.getRideHistory(customerId, driverId)

                // Transformar lista de `Ride` em lista de `RideHistoryUiModel`
                val uiModelList = response.rides.map { ride ->
                    RideHistoryUiModel(
                        rideId = ride.id,
                        driverName = ride.driver.name,
                        date = ride.date,
                        origin = ride.origin,
                        destination = ride.destination,
                        distance = ride.distance,
                        duration = ride.duration,
                        value = ride.value
                    )
                }

                _rideHistoryState.value = Result.success(uiModelList)

                // Extraindo motoristas únicos
                val uniqueDrivers = response.rides.map { it.driver }.distinctBy { it.id }
                _driversState.value = Result.success(uniqueDrivers)
            } catch (e: ApiException) {
                _rideHistoryState.value = Result.failure(e)
                _errorMessage.postValue(ErrorUtils.ApiErrorCode.fromCode(e.errorCode).userMessage)
            } catch (e: Exception) {
                _rideHistoryState.value = Result.failure(e)
                _errorMessage.postValue(ErrorUtils.ApiErrorCode.UNKNOWN_ERROR.userMessage)
            }
        }
    }
}


