package com.devgiul.mychofer.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.devgiul.mychofer.R
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject

fun View.esconderTeclado(){

    val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)

}

fun Activity.exibirMensagem(texto: String) {
    val rootView = this.findViewById<View>(android.R.id.content)
    val snackbar = Snackbar.make(rootView, texto, Snackbar.LENGTH_LONG)

    // Alterar cor do fundo
    snackbar.view.setBackgroundColor(Color.parseColor("#2196F3")) // Azul

    // Estilizar o texto
    val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(Color.YELLOW) // Texto amarelo
    textView.textSize = 16f // Tamanho do texto
    textView.setTypeface(textView.typeface, Typeface.BOLD) // Negrito

    snackbar.show()
}



fun <T>Activity.navegarPara( destino: Class<T>, finalizar: Boolean = true ){
    startActivity(
        Intent(this, destino)
    )
    if (finalizar) {
        // isso pode ser feito com um delay ou verificando se a nova atividade está em primeiro plano
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 500)  // 500ms de delay, ajustável conforme necessário
    }
}

fun parseErrorCode(errorBody: String): String {
    Log.e("ErrorBody", "Erro da API: $errorBody")
    return try {
        val json = JSONObject(errorBody)
        json.getString("error_code")
    } catch (e: JSONException) {
        Log.e("ParseError", "Erro ao parsear o código: ${e.message}")
        ErrorUtils.ApiErrorCode.UNKNOWN_ERROR.code
    }
}

class ApiException(
    val errorCode: String,
    override val message: String
) : Exception(message)