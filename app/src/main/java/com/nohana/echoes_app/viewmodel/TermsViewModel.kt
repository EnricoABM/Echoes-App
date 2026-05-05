package com.nohana.echoes_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nohana.echoes_app.service.network.TermsNetworkService
import com.nohana.echoes_app.view.state.TermsActivityState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

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

    /** Estado atual, observável pela UI. */
    val state = _state.asStateFlow()

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
}