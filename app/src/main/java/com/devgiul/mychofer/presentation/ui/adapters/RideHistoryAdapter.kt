package com.devgiul.mychofer.presentation.ui.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devgiul.mychofer.R
import com.devgiul.mychofer.model.RideHistoryUiModel
import java.text.SimpleDateFormat
import java.util.Locale

class RideHistoryAdapter : RecyclerView.Adapter<RideHistoryAdapter.RideViewHolder>() {

    var rideList = listOf<RideHistoryUiModel>()

    fun submitList(rides: List<RideHistoryUiModel>) {
        rideList = rides
        notifyDataSetChanged()
    }

    inner class RideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTime = itemView.findViewById<TextView>(R.id.tvDateTime)
        private val driverName = itemView.findViewById<TextView>(R.id.tvDriverName)
        private val originDestination = itemView.findViewById<TextView>(R.id.tvOriginDestination)
        private val distanceDuration = itemView.findViewById<TextView>(R.id.tvDistanceDuration)
        private val value = itemView.findViewById<TextView>(R.id.tvValue)

        fun bind(ride: RideHistoryUiModel) {

            // Formatar a data e hora para "dd/MM/yy HH:mm"
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
            val date = inputFormat.parse(ride.date)
            val formattedDate = outputFormat.format(date)
            dateTime.text = "Data: $formattedDate"


            driverName.text = "Motorista: ${ride.driverName}"
            driverName.setTypeface(null, Typeface.BOLD)

            originDestination.text = "Origem: ${ride.origin}\nDestino: ${ride.destination}"

            // Formatar a distância para 2 casas decimais
            val formattedDistance = String.format("%.2f", ride.distance)
            distanceDuration.text = "Distância: $formattedDistance km | Duração: ${ride.duration}"

            // Formatar o valor para 2 casas decimais
            val formattedValue = String.format("R$ %.2f", ride.value)
            value.text = "Valor: $formattedValue"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return RideViewHolder(view)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        holder.bind(rideList[position])
    }

    override fun getItemCount(): Int = rideList.size
}
