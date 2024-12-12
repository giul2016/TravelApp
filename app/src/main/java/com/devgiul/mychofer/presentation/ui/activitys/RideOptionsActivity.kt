package com.devgiul.mychofer.presentation.ui.activitys

import android.os.Bundle
import android.util.Log
import android.webkit.WebView

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.devgiul.mychofer.R

import com.devgiul.mychofer.databinding.ActivityRideOptionsBinding
import com.devgiul.mychofer.model.DriverModel
import com.devgiul.mychofer.model.RideConfirmModel
import com.devgiul.mychofer.model.RideEstimateModel
import com.devgiul.mychofer.model.RideOptionModel
import com.devgiul.mychofer.presentation.ui.adapters.DriverAdapter
import com.devgiul.mychofer.presentation.viewmodel.RideOptionsViewModel
import com.devgiul.mychofer.utils.AlertaCarregamento
import com.devgiul.mychofer.utils.Result
import com.devgiul.mychofer.utils.exibirMensagem
import com.devgiul.mychofer.utils.navegarPara
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RideOptionsActivity : AppCompatActivity() {

    private val alertaCarregamento by lazy {
        AlertaCarregamento(this)
    }

    private lateinit var binding: ActivityRideOptionsBinding
    private val viewModel: RideOptionsViewModel by viewModels()

    private var rideEstimate: RideEstimateModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRideOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa a API do Google Maps
        Places.initialize(applicationContext, getString(R.string.maps_api_key))

        binding.backBtn.setOnClickListener {
            navegarPara(RideRequestActivity::class.java)
        }

        setupObservers()

        val rideEstimate: RideEstimateModel? = intent.extras?.getParcelable("rideEstimateModel")
        this.rideEstimate = rideEstimate

        if (rideEstimate != null) {
            Log.d("LISTA_RIDE", "Lista de motoristas: $rideEstimate")
            setupUI(rideEstimate)
        } else {
            exibirMensagem("Erro ao carregar as opções de viagem.")
        }
    }

    private fun setupUI(response: RideEstimateModel) {
        // Configura URL do mapa estático com coordenadas de origem e destino
        val staticMapUrl = gerarUrlMapaEstatico(
            response.origin.latitude,
            response.origin.longitude,
            response.destination.latitude,
            response.destination.longitude
        )

        // Log da URL gerada
        Log.d("MAP_URL", "URL do Google Maps: $staticMapUrl")

        // Usa Glide para carregar a imagem do mapa
        Glide.with(this)
            .load(staticMapUrl)
            .into(binding.ivMap)

        // Configura a lista de motoristas
        val adapter = DriverAdapter(response.options) { selectedOption ->
            confirmRide(selectedOption)
        }
        binding.rvDrivers.adapter = adapter
        binding.rvDrivers.layoutManager = LinearLayoutManager(this)
    }

    private fun gerarUrlMapaEstatico(
        origemLat: Double,
        origemLng: Double,
        destinoLat: Double,
        destinoLng: Double
    ): String {
        return "https://maps.googleapis.com/maps/api/staticmap?" +
                "size=600x300" +
                "&markers=color:blue|label:A|$origemLat,$origemLng" +
                "&markers=color:red|label:B|$destinoLat,$destinoLng" +
                "&path=color:0x0000ff|weight:5|$origemLat,$origemLng|$destinoLat,$destinoLng" +
                "&key=${getString(R.string.maps_api_key)}"
    }

    private fun setupObservers() {
        viewModel.confirmRideState.observe(this) { result ->
            when (result) {
                is Result.Loading -> alertaCarregamento.exibir("")
                is Result.Success -> {
                    alertaCarregamento.fechar()
                    exibirMensagem(result.data.message)
                    navigateToHistory()
                }
                is Result.Failure -> {
                    alertaCarregamento.fechar()
                    exibirMensagem(result.exception.message ?: "Erro ao confirmar viagem.")
                }
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            exibirMensagem(error)
        }
    }

    private fun confirmRide(option: RideOptionModel) {
        val estimate = rideEstimate ?: return

        val customerId = intent.extras?.getString("customerId") ?: ""
        val request = RideConfirmModel(
            customerId = customerId,
            origin = estimate.origin.toString(),
            destination = estimate.destination.toString(),
            distance = estimate.distance,
            duration = estimate.duration,
            driver = DriverModel(id = option.id, name = option.name),
            value = option.value
        )

        viewModel.confirmRide(request)
    }

    private fun navigateToHistory() {
        navegarPara(HistoryActivity::class.java)
    }
}

