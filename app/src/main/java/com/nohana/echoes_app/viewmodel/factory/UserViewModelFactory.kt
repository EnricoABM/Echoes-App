package com.nohana.echoes_app.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.service.network.UserNetworkService
import com.nohana.echoes_app.viewmodel.UserViewModel

class UserViewModelFactory(
    private val baseUrl: String,
    private val context: Context
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val retrofit = NetworkProvider.Companion.getRetofitWithJwtInterceptor(baseUrl, context)
        val service = retrofit.create(UserNetworkService::class.java)

        return UserViewModel(service) as T
    }
}