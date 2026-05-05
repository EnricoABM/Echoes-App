package com.nohana.echoes_app.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.service.network.TermsNetworkService
import com.nohana.echoes_app.viewmodel.TermsViewModel

/**
 * Factory responsável por criar instâncias de [TermsViewModel].
 *
 * Utiliza o Retrofit sem interceptor JWT, pois os termos são públicos
 * e acessíveis antes da autenticação.
 *
 * @property baseUrl URL base do servidor.
 * @property context Contexto da aplicação.
 */
class TermsViewModelFactory(
    private val baseUrl: String,
    private val context: Context
) : ViewModelProvider.Factory {

    /**
     * Cria o [TermsViewModel] com o [TermsNetworkService] injetado.
     *
     * @param modelClass Classe do ViewModel a ser criado.
     * @param extras     Extras fornecidos pelo [ViewModelProvider].
     */
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val retrofit = NetworkProvider.getRetrofitInstance(baseUrl, context)
        val termsService = retrofit.create(TermsNetworkService::class.java)
        return TermsViewModel(termsService) as T
    }
}