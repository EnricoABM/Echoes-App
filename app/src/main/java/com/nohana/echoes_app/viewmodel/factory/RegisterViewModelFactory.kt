package com.nohana.echoes_app.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.service.RegisterNetworkService
import com.nohana.echoes_app.viewmodel.RegisterViewModel

class RegisterViewModelFactory(
    val baseUrl: String,
    val context: Context
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        val retrofit = NetworkProvider.getRetrofitInstance(baseUrl, context)
        val service = retrofit.create(RegisterNetworkService::class.java)

        return RegisterViewModel(service) as T
    }
}