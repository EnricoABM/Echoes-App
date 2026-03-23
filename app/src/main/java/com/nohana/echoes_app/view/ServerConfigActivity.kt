package com.nohana.echoes_app.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import com.nohana.echoes_app.data.ServerStorage
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.screen.ServerConfigScreen

class ServerConfigActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val serverStorage = ServerStorage(applicationContext)

        setContent {
            var errorMessage by remember { mutableStateOf("") }

            EchoesAppTheme {
                Column {
                    TitleComponent("Servidor")

                    ServerConfigScreen(
                        currentAddress = serverStorage.getAddress()
                            .removePrefix("http://")
                            .removeSuffix(":8080/"),
                        onSave = { ip ->
                            if (ip.isBlank()) {
                                errorMessage = "Informe um endereço válido"
                                return@ServerConfigScreen
                            }
                            // monta a URL completa
                            val address = "http://$ip:8080/"
                            serverStorage.setAddress(address)

                            finish()
                        },
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }
}