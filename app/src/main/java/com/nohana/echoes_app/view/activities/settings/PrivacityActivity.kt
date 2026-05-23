package com.nohana.echoes_app.view.activities.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class PrivacityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrivacityScreen(
                onOpenTerms = {},
                onRevokeTerms = {},
                onExportData = {}
            )
        }
    }
}

