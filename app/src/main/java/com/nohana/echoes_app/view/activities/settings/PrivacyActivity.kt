package com.nohana.echoes_app.view.activities.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nohana.echoes_app.MainActivity
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.view.activities.terms.TermsActivity
import com.nohana.echoes_app.view.states.TermsEvent
import com.nohana.echoes_app.viewmodel.AuthViewModel
import com.nohana.echoes_app.viewmodel.TermsViewModel
import com.nohana.echoes_app.viewmodel.UserViewModel
import com.nohana.echoes_app.viewmodel.factory.AuthViewModelFactory
import com.nohana.echoes_app.viewmodel.factory.TermsViewModelFactory
import com.nohana.echoes_app.viewmodel.factory.UserViewModelFactory

class PrivacyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val termsViewModel: TermsViewModel by viewModels {
            TermsViewModelFactory(
                NetworkProvider.getAddress(),
                applicationContext
            )
        }

        val userViewModel: UserViewModel by viewModels {
            UserViewModelFactory(
                NetworkProvider.getAddress(),
                applicationContext
            )
        }

        val authViewModel: AuthViewModel by viewModels {
            AuthViewModelFactory(
                NetworkProvider.getAddress(),
                applicationContext
            )
        }

        setContent {

            val snackbarHostState = remember {
                SnackbarHostState()
            }

            LaunchedEffect(Unit) {
                termsViewModel.event.collect { event ->
                    when(event) {
                        TermsEvent.SuccessRevokeTerms -> {
                            snackbarHostState.showSnackbar(
                                "Termos Revogados com Sucesso.\nVocê será desconectado...",
                            )
                            authViewModel.logout()
                            startActivity(
                                Intent(this@PrivacyActivity, MainActivity::class.java)
                            )
                            finish()
                        }
                        is TermsEvent.Error -> {
                            snackbarHostState.showSnackbar(
                                event.message
                            )
                        }
                    }
                }
            }

            PrivacityScreen(
                onOpenTerms = {
                    startActivity(
                        Intent(this@PrivacyActivity, TermsActivity::class.java)
                    )
                },
                onRevokeTerms = {
                    termsViewModel.revokeTerms()
                },
                onExportData = {},
                onBackFunction = {
                    finish()
                },
                snackbar = snackbarHostState
            )
        }
    }
}

