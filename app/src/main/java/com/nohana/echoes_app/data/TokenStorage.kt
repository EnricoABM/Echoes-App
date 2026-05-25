package com.nohana.echoes_app.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.health.connect.datatypes.ActiveCaloriesBurnedRecord
import android.os.Build
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.nohana.echoes_app.security.Crypto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.apply

class TokenStorage(private val context: Context) {

    private val JWT_KEY = "jwt"
    private val sharedPreferences: SharedPreferences by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        EncryptedSharedPreferences.create(
            "token_storage",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun setToken(token: String) {
        sharedPreferences
            .edit()
            .putString(JWT_KEY, token)
            .apply()
    }

    fun getToken(): String? {
        val token = sharedPreferences.getString(JWT_KEY, null)
        return token;
    }

    fun clear() {
        sharedPreferences
            .edit()
            .remove(JWT_KEY)
            .apply()
    }
}















