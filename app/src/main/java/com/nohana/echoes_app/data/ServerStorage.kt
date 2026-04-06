package com.nohana.echoes_app.data

import android.content.Context

class ServerStorage(context: Context) {

    private val prefs = context.getSharedPreferences("server_prefs", Context.MODE_PRIVATE)

    fun getAddress(): String {
        return prefs.getString("server_address", "") ?: ""
    }

    fun setAddress(address: String) {
        val address = "https://$address:8080/"
        prefs.edit().putString("server_address", address).apply()
    }

    fun hasAddress(): Boolean {
        return getAddress().isNotBlank()
    }
}