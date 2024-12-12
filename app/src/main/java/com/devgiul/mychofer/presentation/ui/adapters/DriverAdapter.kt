package com.devgiul.mychofer.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devgiul.mychofer.R
import com.devgiul.mychofer.model.RideOptionModel

class DriverAdapter(
    private val drivers: List<RideOptionModel>,
    private val onDriverSelected: (RideOptionModel) -> Unit
) : RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {

    inner class DriverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val description: TextView = view.findViewById(R.id.tvDescription)
        val vehicle: TextView = view.findViewById(R.id.tvVehicle)
        val rating: TextView = view.findViewById(R.id.tvRating)
        val value: TextView = view.findViewById(R.id.tvValue)
        val chooseButton: Button = view.findViewById(R.id.btnChoose)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_driver, parent, false)
        return DriverViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        val driver = drivers[position]
        holder.name.text = "Motorista: ${driver.name}"
        holder.description.text = "Sobre: ${driver.description}"
        holder.vehicle.text = "Carro: ${driver.vehicle}"
        holder.rating.text = "Rate: ${driver.review.rating}"
        holder.value.text = "Preço: R$ ${driver.value}"

        holder.chooseButton.setOnClickListener {
            onDriverSelected(driver)
        }
    }

    override fun getItemCount(): Int = drivers.size
}
