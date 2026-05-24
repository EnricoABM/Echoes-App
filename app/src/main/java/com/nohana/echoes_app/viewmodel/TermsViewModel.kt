package com.nohana.echoes_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.service.network.TermsNetworkService
import com.nohana.echoes_app.view.activities.terms.TermsActivityState
import com.nohana.echoes_app.view.states.TermsEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * ViewModel responsável por carregar os termos disponíveis no sistema
 * para exibição na [TermsActivity].
 *
 * @property termsNetworkService Serviço Retrofit para chamadas de termos.
 */
class TermsViewModel(
    private val termsNetworkService: TermsNetworkService
) : ViewModel() {

    companion object {
        const val TERMS_OF_USE = "TERMS_OF_USE"
        const val PRIVACY_POLICY = "PRIVACY_POLICY"
    }

    private val _state = MutableStateFlow<TermsActivityState>(TermsActivityState.Loading)
    val state = _state.asStateFlow()


    private val _event = MutableSharedFlow<TermsEvent>()
    val event = _event.asSharedFlow()

    init {
        loadTerms()
    }

    /**
     * Carrega os Termos de Uso e a Política de Privacidade em paralelo.
     *
     * Em caso de falha em qualquer um dos dois, emite [TermsActivityState.Error].
     */
    fun loadTerms() {
        viewModelScope.launch {
            _state.update { TermsActivityState.Loading }
            try {
                val termsOfUseDeferred = async { termsNetworkService.getTerms(TERMS_OF_USE) }
                val privacyDeferred = async { termsNetworkService.getTerms(PRIVACY_POLICY) }

                val termsOfUseResponse = termsOfUseDeferred.await()
                val privacyResponse = privacyDeferred.await()

                if (termsOfUseResponse.isSuccessful && termsOfUseResponse.body() != null &&
                    privacyResponse.isSuccessful && privacyResponse.body() != null
                ) {
                    _state.update {
                        TermsActivityState.Success(
                            termsOfUse = termsOfUseResponse.body()!!,
                            privacyPolicy = privacyResponse.body()!!
                        )
                    }
                } else {
                    _state.update {
                        TermsActivityState.Error("Não foi possível carregar os termos.")
                    }
                }
            } catch (e: IOException) {
                _state.update {
                    TermsActivityState.Error("Erro de conexão.")
                }
            }
        }
    }

    /**
     * Revoga os termos aceitos pelo usuário.
     *
     * Emite [TermsEvent.SucessRevokeTerms] em caso de sucesso.
     * Emite [TermsEvent.Error] em caso de falha de conexão.
     * */
    fun revokeTerms() {
        viewModelScope.launch {
            try {
                val response = termsNetworkService.revokeTerms()

                if (response.isSuccessful) {
                    _event.emit(TermsEvent.SuccessRevokeTerms)
                } else {
                    _event.emit(TermsEvent.Error("Não foi possível revogar os termos, tente novamente mais tarde."))
                }
            } catch (e: SocketTimeoutException) {
                _event.emit(TermsEvent.Error("Erro de Conexão, verifique sua conexão"))
            }
        }
    }
}