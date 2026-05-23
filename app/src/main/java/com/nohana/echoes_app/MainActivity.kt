package com.nohana.echoes_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.activities.auth.AuthActivity
import com.nohana.echoes_app.view.activities.auth.RegisterActivity
import com.nohana.echoes_app.view.screens.InitialScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EchoesAppTheme {
                InitialScreen(
                    onLogin = {
                        startActivity(
                            Intent(baseContext, AuthActivity::class.java)
                        )
                    },
                    onRegister = {
                        startActivity(
                            Intent(baseContext, RegisterActivity::class.java)
                        )
                    }
                )
            }
        }
    }
}