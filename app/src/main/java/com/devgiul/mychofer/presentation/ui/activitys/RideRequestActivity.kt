package com.devgiul.mychofer.presentation.ui.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devgiul.mychofer.R
import com.devgiul.mychofer.presentation.viewmodel.RideViewModel
import com.devgiul.mychofer.utils.AlertaCarregamento
import com.devgiul.mychofer.utils.ExitBottomSheetUtil
import com.devgiul.mychofer.utils.exibirMensagem
import com.devgiul.mychofer.utils.navegarPara
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RideRequestActivity : AppCompatActivity() {

    private val alertaCarregamento by lazy {
        AlertaCarregamento(this)
    }
    private lateinit var customerIdEditText: EditText
    private lateinit var originEditText: EditText
    private lateinit var destinationEditText: EditText
    private lateinit var estimateButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var history: ImageView
    private lateinit var exit: ImageView
    private lateinit var customerId: String

    private val viewModel: RideViewModel by viewModels()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_request)

        //Inicializando as Views
        customerIdEditText = findViewById(R.id.etCustomerId)
        originEditText = findViewById(R.id.etOrigin)
        destinationEditText = findViewById(R.id.etDestination)
        estimateButton = findViewById(R.id.btnEstimate)
        resultTextView = findViewById(R.id.tvResult)
        history = findViewById(R.id.btnConfig)
        exit = findViewById(R.id.imgSair)

        exit.setOnClickListener {
            exit()
        }

        //botão para navegar para tela de perfil
        history.setOnClickListener {
            navegarPara(HistoryActivity::class.java,false)
        }

        // Configurando o clique do botão para buscar motorista
        estimateButton.setOnClickListener {
            customerId = customerIdEditText.text.toString()
            val origin = originEditText.text.toString()
            val destination = destinationEditText.text.toString()

            viewModel.estimateRide(customerId, origin, destination)

        }

        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                alertaCarregamento.exibir("")
            } else {
                alertaCarregamento.fechar()
            }
        })

        // Observando os dados da ViewModel
        viewModel.rideOptions.observe(this) { response ->
            resultTextView.text = "Motoristas encontrados: ${response.options.size}"
            exibirMensagem("Motoristas encontrados: ${response.options.size}")

            Log.d("LISTA_RIDE_MOTORISTA", "lista de motoristas $response ")

            // Verifica se existem motoristas encontrados
            if (response.options.isEmpty()) {
                // Caso não existam motoristas, exibe a mensagem
                exibirMensagem("Nenhum motorista encontrado!")
                return@observe
            }

            val intent = Intent(this, RideOptionsActivity::class.java).apply {
                val bundle = Bundle()
                bundle.putParcelable("rideEstimateModel", response) //
                bundle.putString("customerId", customerId)
                putExtras(bundle)
            }
            startActivity(intent)
        }

        viewModel.errorMessage.observe(this) { error ->
            resultTextView.text = "Erro: $error"
            exibirMensagem(error)
        }


    }

    private fun exit() {
        ExitBottomSheetUtil.showExitDialog(
            activity = this,
            message = "Deseja sair do aplicativo?",
            onExit = {
                finishAffinity()
            }
        )
    }

    override fun onBackPressed() {
        // Exibe o BottomSheetDialog ao pressionar o botão "Voltar" do dispositivo
        ExitBottomSheetUtil.showExitDialog(
            activity = this,
            message = "Deseja realmente sair?",
            onExit = {
                // Finaliza o app somente se o usuário confirmar
                super.onBackPressed()
                finishAffinity() // Fecha todas as atividades
            }
        )
    }
}
