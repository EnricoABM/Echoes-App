package com.nohana.echoes_app.view.activities.auth

import com.nohana.echoes_app.network.dto.TermsResponseDTO

sealed interface RegisterState {
    data object Register : RegisterState
    data object Loading : RegisterState
    data object ValidEmail : RegisterState
    data object Success : RegisterState

    data class RegisterValidationError(
        val nameError: String? = null,
        val emailError: String? = null,
        val passwordError: String? = null,
        val confirmPasswordError: String? = null,
        val termsError: String? = null
    ) : RegisterState

    data class RegisterError(
        val message: String
    ) : RegisterState

    data class ViewTerms(
        val termsOfUse: TermsResponseDTO,
        val privacyPolicy: TermsResponseDTO
    ) : RegisterState

    data class ViewTermsError(
        val message: String
    ) : RegisterState

    data class CodeValidationError(
        val codeError: String? = null
    ) : RegisterState

    data class CodeError(
        val message: String
    ) : RegisterState
}