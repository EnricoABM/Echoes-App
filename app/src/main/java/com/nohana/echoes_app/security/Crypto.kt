package com.nohana.echoes_app.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object Crypto {
    private const val KEY_ALIAS = "secret"
    private const val ALGORITM = KeyProperties.KEY_ALGORITHM_AES
    private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
    private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
    private const val TRANSFORMATION = "$ALGORITM/$BLOCK_MODE/$PADDING"

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
    }

    public fun encrypt(text: String): String {
        // Inicialização do objeto com a configuração de criptografia
        val cipher = Cipher.getInstance(TRANSFORMATION)

        // Inicialização do objeto para criptografar dados
        cipher.init(Cipher.ENCRYPT_MODE, getKey())

        // Definição do Valor de Inicialização
        val iv = cipher.iv

        // Dados Criptografados
        val encryptedBytes = cipher.doFinal(text.toByteArray(Charsets.UTF_8))

        // Transformação dos dados binários em valor textual
        val encrypted = Base64.encodeToString(iv + encryptedBytes, Base64.NO_WRAP)
        return encrypted
    }

    public fun decrypted(data: String): String {
        // Decodificador do valor textual
        val decoded = Base64.decode(data, Base64.NO_WRAP)

        // Inicialização do objeto com a configuração de descriptografia
        val cipher = Cipher.getInstance(TRANSFORMATION)

        // Particionando os valores da cifra
        val iv = decoded.copyOfRange(0, cipher.blockSize)
        val encryptedBytes = decoded.copyOfRange(cipher.blockSize, decoded.size)

        // Inicialização do objeto para descriptografar dados
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))

        // Bytes descriptografados
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        // Transformação dos bytes em valor textual
        val decrypted = String(decryptedBytes, Charsets.UTF_8)
        return decrypted
    }

    private fun createKey(): SecretKey {
        // Criação da chave de criptografia
        return KeyGenerator
            .getInstance(ALGORITM) // Definição do Algoritmo
            .apply {
                init(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS, // Nome da chave
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT // Proposito da chave
                    )
                        .setBlockModes(BLOCK_MODE) // Modo de operação
                        .setEncryptionPaddings(PADDING) // Modo de preenchimento
                        .setRandomizedEncryptionRequired(true) // Randomização de Criptografia
                        .setUserAuthenticationRequired(false) // Sem necessidade de autorização biometrica
                        .build()
                )
            }.generateKey()
    }

    private fun getKey(): SecretKey {
        // Verifica a existencia da chave, do contrário, o método cria uma nova
        val existingKey = keyStore
            .getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

}