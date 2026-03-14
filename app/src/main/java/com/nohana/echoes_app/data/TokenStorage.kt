package com.nohana.echoes_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStorage: DataStore<Preferences> by preferencesDataStore(name = "token")
class TokenStorage(private val context: Context) {
    companion object {
        val TOKEN = stringPreferencesKey("token")
    }

    suspend fun setToken(token: String) {
        context.dataStorage.edit { prefs ->
            prefs[TOKEN] = token
        }
    }

    val token: Flow<String> = context.dataStorage.data.map { preferences ->
        preferences[TOKEN] as String ?: ""
    }

    suspend fun getToken(): String {
        return context.dataStorage.data.map {
            it[TOKEN] ?: ""
        }.first()
    }
}

















