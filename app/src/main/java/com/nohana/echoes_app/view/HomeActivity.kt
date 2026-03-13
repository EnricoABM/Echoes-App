package com.nohana.echoes_app.view

import android.media.session.MediaSession
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.nohana.echoes_app.data.TokenStorage
import com.nohana.echoes_app.data.dataStorage
import kotlinx.coroutines.launch

class HomeActivity(): ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tokenManager = TokenStorage(this@HomeActivity)
        setContent {
            val token = tokenManager.token.collectAsState(initial = "")

            Text(text = token.value)
        }
    }
}