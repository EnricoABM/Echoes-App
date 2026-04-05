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
        val cipher = Cipher.getInstance(TRANSFORMATION)

        cipher.init(Cipher.ENCRYPT_MODE, getKey())

        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(text.toByteArray(Charsets.UTF_8))

        val encrypted = Base64.encodeToString(iv + encryptedBytes, Base64.NO_WRAP)
        Log.d("ENCRYPTED", encrypted)
        return encrypted
    }

    public fun decrypted(data: String): String {
        val decoded = Base64.decode(data, Base64.NO_WRAP)

        val cipher = Cipher.getInstance(TRANSFORMATION)

        val iv = decoded.copyOfRange(0, cipher.blockSize)
        val encryptedBytes = decoded.copyOfRange(cipher.blockSize, decoded.size)
        cipher.init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))

        val decryptedBytes = cipher.doFinal(encryptedBytes)

        val decrypted = String(decryptedBytes, Charsets.UTF_8)
        Log.d("DECRYPTED", decrypted)
        return decrypted
    }

    private fun createKey(): SecretKey {
        return KeyGenerator
            .getInstance(ALGORITM)
            .apply {
                init(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(PADDING)
                        .setRandomizedEncryptionRequired(true)
                        .setUserAuthenticationRequired(false)
                        .build()
                )
            }.generateKey()
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore
            .getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

}