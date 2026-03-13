package com.nohana.echoes_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


class TokenStorage {

    val Context.dataStorage: DataStore<Preferences> by preferencesDataStore(name = "token")
}

















