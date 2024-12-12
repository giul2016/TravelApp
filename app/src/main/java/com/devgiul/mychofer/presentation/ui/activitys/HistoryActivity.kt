package com.devgiul.mychofer.presentation.ui.activitys

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devgiul.mychofer.data.dto.Driver
import com.devgiul.mychofer.databinding.ActivityHistoryBinding
import com.devgiul.mychofer.model.DriverItem
import com.devgiul.mychofer.model.RideHistoryUiModel
import com.devgiul.mychofer.presentation.ui.adapters.RideHistoryAdapter
import com.devgiul.mychofer.presentation.viewmodel.RideHistoryViewModel
import com.devgiul.mychofer.utils.AlertaCarregamento
import dagger.hilt.android.AndroidEntryPoint
import com.devgiul.mychofer.utils.Result
import com.devgiul.mychofer.utils.exibirMensagem
import com.devgiul.mychofer.utils.navegarPara

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {
    private val alertaCarregamento by lazy {
        AlertaCarregamento(this)
    }

    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: RideHistoryViewModel by viewModels()
    private val adapter by lazy { RideHistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = adapter

        // Inicializar o estado da Activity
        initializeState()

        backHome()

        // Observar o estado dos motoristas e histórico das viagens
        observeDriversState()
        observeRideHistoryState()

        // Observar mensagens de erro
        observeErrorMessages()

        // Configurar clique no botão de filtro
        binding.btnApplyFilter.setOnClickListener {
            val customerId = binding.etUserId.text.toString()
            val driverId = getSelectedDriverId()

            if (customerId.isBlank()) {
                // Se o campo de ID do usuário estiver vazio, limpar a lista e resetar o spinner
                populateRideRecyclerView(emptyList())
                exibirMensagem("Por favor, insira o ID do usuário para aplicar o filtro.")

                // Resetar o spinner para o estado sem seleção (sem opções visíveis)
                resetDriverSpinnerToEmpty()
            } else {
                // Caso contrário, buscar o histórico de viagens
                viewModel.getRideHistory(customerId, driverId)

                // Limpar o campo de ID do usuário
                binding.etUserId.text.clear()

                // Resetar o spinner para o estado inicial com "Todos"
                resetDriverSpinnerToDefault()

                // Desabilitar o botão por 5 segundos
                disableButtonTemporarily()
            }
        }
    }

    private fun initializeState() {
        // Resetar o spinner e a lista quando a Activity for aberta pela primeira vez
        resetDriverSpinnerToEmpty() // Não selecionar nada no spinner
        populateRideRecyclerView(emptyList()) // Inicializa a lista vazia
    }

    private fun observeRideHistoryState() {
        viewModel.rideHistoryState.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    // Exibir carregamento
                    alertaCarregamento.exibir("")
                }

                is Result.Success -> {
                    val rideHistoryUiList = result.data
                    if (!rideHistoryUiList.isNullOrEmpty()) {
                        populateRideRecyclerView(rideHistoryUiList) // Passar lista para RecyclerView
                    }
                    alertaCarregamento.fechar()
                }

                is Result.Failure -> {
                    // Lidar com erros
                    alertaCarregamento.fechar()
                    exibirMensagem(
                        result.exception.message ?: "Erro ao carregar histórico de viagens."
                    )
                    resetDriverSpinnerToEmpty()
                }
            }
        }

        // Observar motoristas únicos
        viewModel.driversState.observe(this) { driverResult ->
            if (driverResult is Result.Success) {
                val uniqueDrivers = driverResult.data
                if (!uniqueDrivers.isNullOrEmpty()) {
                    populateDriverSpinner(uniqueDrivers)
                }
            }
        }
    }

    private fun observeDriversState() {
        viewModel.driversState.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    // Exibir um carregamento opcional
                    alertaCarregamento.exibir("")
                }

                is Result.Success -> {
                    alertaCarregamento.fechar()
                    val drivers = result.data
                    if (drivers != null) {
                        populateDriverSpinner(drivers)
                    }
                }

                is Result.Failure -> {
                    exibirMensagem(result.exception.message ?: "Erro ao carregar informações.")

                }
            }
        }
    }

    // Função para observar mensagens de erro
    private fun observeErrorMessages() {
        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                exibirMensagem(errorMessage)
            }
        }
    }

    private fun populateRideRecyclerView(rides: List<RideHistoryUiModel>) {
        adapter.submitList(rides)
    }

    // Declaração da lista fixa de motoristas
    private val fixedDrivers = listOf(
        DriverItem("1", "Homer Simpson"),
        DriverItem("2", "Dominic Toretto"),
        DriverItem("3", "James Bond")
    )

    private fun populateDriverSpinner(drivers: List<Driver>) {
        // Extraindo os nomes dos motoristas fixos
        val driverNames = mutableListOf("Todos") // Opção para visualizar todas as viagens
        driverNames.addAll(fixedDrivers.map { it.name })

        // Configurar o adaptador do Spinner com a lista fixa de motoristas
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, driverNames)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spDriverFilter.adapter = spinnerAdapter

        // Listener para o Spinner
        binding.spDriverFilter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedDriverName = driverNames[position]
                    filterRidesByDriver(selectedDriverName, drivers)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Caso não tenha selecionado nada, mostra todas as viagens
                    filterRidesByDriver("Todos", drivers)

                }
            }
    }

    private fun filterRidesByDriver(selectedDriver: String, drivers: List<Driver>) {
        // Verifica se "Todos" foi selecionado no spinner
        if (selectedDriver == "Todos") {
            // Exibe todas as viagens, sem filtrar por motorista
            viewModel.rideHistoryState.value?.let { result ->
                if (result is Result.Success) {
                    // Atualiza a RecyclerView com todas as viagens
                    populateRideRecyclerView(result.data)
                } else {
                    // Caso o estado não seja um sucesso, exibe a mensagem de erro
                    exibirMensagem("Erro ao carregar viagens.")
                }
            }
            return
        }

        // Verifica se o motorista selecionado está na lista fixa
        val selectedDriverInFixedList = fixedDrivers.find { it.name == selectedDriver }

        if (selectedDriverInFixedList != null) {
            // Caso o motorista esteja na lista fixa, filtra as viagens
            viewModel.rideHistoryState.value?.let { result ->
                if (result is Result.Success) {
                    // Filtra as viagens baseadas no motorista selecionado
                    val filteredRides = result.data.filter { it.driverName == selectedDriver }

                    if (filteredRides.isNotEmpty()) {
                        // Atualiza a RecyclerView com as viagens filtradas
                        populateRideRecyclerView(filteredRides)
                    } else {
                        // Caso não haja viagens para o motorista selecionado
                        exibirMensagem("Não existem viagens para o motorista ${selectedDriver}.")
                    }
                } else {
                    // Caso o estado não seja um sucesso, exibe a mensagem de erro
                    exibirMensagem("Erro ao carregar viagens.")
                }
            }
        } else {
            // Caso o motorista não esteja na lista fixa, exibe mensagem de erro
            exibirMensagem("Motorista não encontrado!")
        }
    }


    private fun getSelectedDriverId(): Int? {
        val selectedDriver = binding.spDriverFilter.selectedItem?.toString()
        val rides = adapter.rideList

        val selectedDriverId = rides.find { it.driverName == selectedDriver }?.rideId

        return if (selectedDriver == null || selectedDriver == "Todos") null else selectedDriverId
    }

    // Função para desabilitar o botão por 5 segundos
    private fun disableButtonTemporarily() {
        binding.btnApplyFilter.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            binding.btnApplyFilter.isEnabled = true
        }, 5000)  // Reabilitar após 5 segundos
    }

    // Função para resetar o spinner para o estado sem seleção (sem opções visíveis)
    private fun resetDriverSpinnerToEmpty() {
        val emptyAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, emptyList<String>())
        emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spDriverFilter.adapter = emptyAdapter
        binding.spDriverFilter.setSelection(AdapterView.INVALID_POSITION) // Não selecionar nada
    }

    // Função para resetar o spinner para o estado inicial com "Todos"
    private fun resetDriverSpinnerToDefault() {
        val driverNames = listOf("Todos")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, driverNames)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spDriverFilter.adapter = spinnerAdapter
        binding.spDriverFilter.setSelection(0) // Seleciona a primeira opção "Todos"
    }

    private fun backHome() {
        binding.backBtn.setOnClickListener {
            navegarPara(RideRequestActivity::class.java)
        }

    }

}


