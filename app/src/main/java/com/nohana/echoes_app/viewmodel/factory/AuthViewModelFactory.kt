package com.nohana.echoes_app.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nohana.echoes_app.data.TokenStorage
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.service.network.AuthNetworkService
import com.nohana.echoes_app.viewmodel.AuthViewModel

class AuthViewModelFactory(
    private val baseUrl: String,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val retrofit = NetworkProvider.Companion.getRetrofitInstance(baseUrl, context)
        val service = retrofit.create(AuthNetworkService::class.java)
        val tokenStorage = TokenStorage(context)

        return AuthViewModel(service, tokenStorage) as T
    }
}