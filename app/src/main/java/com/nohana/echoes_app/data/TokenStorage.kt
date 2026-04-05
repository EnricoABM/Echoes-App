package com.nohana.echoes_app.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nohana.echoes_app.security.Crypto
import com.nohana.echoes_app.security.CryptoManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStorage: DataStore<Preferences> by preferencesDataStore(name = "token")
class TokenStorage(private val context: Context) {

    companion object {
        val TOKEN = stringPreferencesKey("jwt_token")
    }

    suspend fun setToken(token: String) {
        context.dataStorage.edit { prefs ->
            prefs[TOKEN] = Crypto.encrypt(token)
        }
    }

    val token: Flow<String?> = context.dataStorage.data.map { preferences ->
        val encrypted = preferences[TOKEN]
        if (encrypted.isNullOrEmpty()) {
            null
        } else {
            try {
                Crypto.decrypted(encrypted)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getToken(): String? {
        return token.first()
    }
}

















