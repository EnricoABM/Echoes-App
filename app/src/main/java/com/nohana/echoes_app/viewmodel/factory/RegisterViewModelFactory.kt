package com.nohana.echoes_app.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.service.network.RegisterNetworkService
import com.nohana.echoes_app.service.network.TermsNetworkService
import com.nohana.echoes_app.viewmodel.RegisterViewModel

/**
 * Factory responsável por criar instâncias de [RegisterViewModel] com
 * as dependências necessárias injetadas manualmente.
 *
 * Utiliza [NetworkProvider.getRetrofitInstance] (sem interceptor JWT),
 * pois o usuário ainda não está autenticado durante o registro.
 *
 * @property baseUrl URL base do servidor, obtida via [NetworkProvider.getAddress].
 * @property context Contexto da aplicação, repassado ao [NetworkProvider].
 */
class RegisterViewModelFactory(
    private val baseUrl: String,
    private val context: Context
) : ViewModelProvider.Factory {

    /**
     * Instancia o [RegisterViewModel] criando os serviços Retrofit necessários.
     *
     * @param modelClass Classe do ViewModel a ser criado.
     * @param extras     Extras de criação fornecidos pelo [ViewModelProvider].
     * @return Instância de [RegisterViewModel] pronta para uso.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val retrofit = NetworkProvider.getRetrofitInstance(baseUrl, context)
        val registerService = retrofit.create(RegisterNetworkService::class.java)
        val termsService = retrofit.create(TermsNetworkService::class.java)
        return RegisterViewModel(registerService, termsService) as T
    }
}