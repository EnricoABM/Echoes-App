package com.nohana.echoes_app.service.validation

object FieldValidatorService {

    fun validateEmail(email: String): ValidationError? {
        if (email.isBlank()) return ValidationError.RequiredField
        if (!email.contains("@")) return ValidationError.InvalidEmail
        return null
    }

    fun validateName(name: String): ValidationError? {
        if (name.isBlank()) return ValidationError.RequiredField
        return null
    }

    fun validatePassword(password: String): ValidationError? {
        if (password.isBlank()) return ValidationError.RequiredField
        if (password.length < 8) return ValidationError.PasswordTooShort
        if (!password.any { it.isUpperCase() }) return ValidationError.PasswordMissingUppercase
        if (!password.any { it.isLowerCase() }) return ValidationError.PasswordMissingLowercase
        if (!password.any { it.isDigit() }) return ValidationError.PasswordMissingDigit
        if (!password.any { !it.isLetterOrDigit() }) return ValidationError.PasswordMissingSpecial
        return null
    }

    fun validatePasswordConfirmation(password: String, confirmation: String): ValidationError? {
        if (password != confirmation) return ValidationError.PasswordsDoNotMatch
        return null
    }

    fun validateCode(code: String): ValidationError? {
        if (code.isBlank()) return ValidationError.RequiredField
        if (code.length != 6) return ValidationError.CodeInvalidLength
        if (!code.all { it.isDigit() }) return ValidationError.CodeNotNumeric
        return null
    }
}