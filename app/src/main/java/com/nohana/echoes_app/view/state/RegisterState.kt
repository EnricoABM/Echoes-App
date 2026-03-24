sealed interface RegisterState {
    object Loading : RegisterState
    object Register : RegisterState
    object Success : RegisterState

    data class ValidEmail(val email: String) : RegisterState

    // Erros gerais
    data class RegisterError(val message: String) : RegisterState
    data class CodeError(val email: String, val message: String) : RegisterState

    // Erros de validação
    data class RegisterValidationError(
        val nameError: String? = null,
        val emailError: String? = null,
        val passwordError: String? = null
    ) : RegisterState

    data class CodeValidationError(
        val email: String,
        val codeError: String? = null
    ) : RegisterState
}