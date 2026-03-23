package com.nohana.echoes_app.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.service.PasswordNetworkService
import com.nohana.echoes_app.viewmodel.PasswordResetViewModel

class PasswordResetViewModelFactory(
    private val baseUrl: String,
    private val context: Context
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val retrofit = NetworkProvider.Companion.getRetrofitInstance(baseUrl, context)
        val service = retrofit.create(PasswordNetworkService::class.java)

        return PasswordResetViewModel(service) as T
    }
}