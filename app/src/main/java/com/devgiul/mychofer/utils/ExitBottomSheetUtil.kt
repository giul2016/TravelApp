package com.devgiul.mychofer.utils

import android.app.Activity
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.widget.Button
import android.widget.TextView
import com.devgiul.mychofer.R

class ExitBottomSheetUtil {

    companion object {

        fun showExitDialog(
            activity: Activity,
            message: String = "Deseja realmente sair?",
            onExit: () -> Unit
        ) {
            // Cria o BottomSheetDialog
            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = LayoutInflater.from(activity).inflate(R.layout.bottom_sheet_exit, null)
            bottomSheetDialog.setContentView(view)

            // Configura a mensagem
            val tvExitMessage = view.findViewById<TextView>(R.id.tvExitMessage)
            tvExitMessage.text = message

            // Configura os botões
            val btnCancel = view.findViewById<Button>(R.id.btnCancel)
            val btnExit = view.findViewById<Button>(R.id.btnExit)

            btnCancel.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            btnExit.setOnClickListener {
                bottomSheetDialog.dismiss()
                onExit() // Executa a ação de saída
            }

            // Exibe o BottomSheet
            bottomSheetDialog.show()
        }
    }
}
