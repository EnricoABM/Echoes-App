package com.nohana.echoes_app.service.validation

sealed class ValidationError(val message: String) {
    // Campos genéricos
    object RequiredField : ValidationError("Campo obrigatório")
    object InvalidEmail : ValidationError("Informe um e-mail válido")
    object InvalidName : ValidationError("Nome inválido")

    // Senha
    object PasswordTooShort : ValidationError("A senha deve ter pelo menos 8 caracteres")
    object PasswordMissingUppercase : ValidationError("A senha deve conter ao menos uma letra maiúscula")
    object PasswordMissingLowercase : ValidationError("A senha deve conter ao menos uma letra minúscula")
    object PasswordMissingDigit : ValidationError("A senha deve conter ao menos um número")
    object PasswordMissingSpecial : ValidationError("A senha deve conter ao menos um caractere especial")
    object PasswordsDoNotMatch : ValidationError("As senhas não coincidem")

    // Código MFA
    object CodeInvalidLength : ValidationError("O código deve ter 6 dígitos")
    object CodeNotNumeric : ValidationError("O código deve conter apenas números")
}