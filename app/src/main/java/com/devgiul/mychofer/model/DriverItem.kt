package com.devgiul.mychofer.model

data class DriverItem(val id: String, val name: String) {
    override fun toString(): String {
        return name // Exibe apenas o nome no Spinner
    }
}

