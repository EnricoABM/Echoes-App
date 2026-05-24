package com.nohana.echoes_app.view.activities.security

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
import com.nohana.echoes_app.view.activities.password.ChangePasswordActivity
import com.nohana.echoes_app.view.states.UserDeleteEvent
import com.nohana.echoes_app.viewmodel.AuthViewModel
import com.nohana.echoes_app.viewmodel.UserViewModel
import com.nohana.echoes_app.viewmodel.factory.AuthViewModelFactory
import com.nohana.echoes_app.viewmodel.factory.UserViewModelFactory

class SecurityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val snackbarHostState = remember {
                SnackbarHostState()
            }

            var showDeleteDialog by remember {
                mutableStateOf(false)
            }

            var showDeleteSuccessDialog by remember {
                mutableStateOf(false)
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

            LaunchedEffect(Unit) {
                userViewModel.event.collect { event ->
                    when (event) {
                        UserDeleteEvent.DeleteAccountSuccess -> {
                            showDeleteSuccessDialog = true
                        }
                        UserDeleteEvent.DeleteRequestSucess ->
                            showDeleteDialog = true
                        is UserDeleteEvent.Error -> { event
                            snackbarHostState.showSnackbar(event.message)
                        }
                    }
                }
            }

            SecurityScreen(
                onBackFunction = {
                    finish()
                },
                onDeleteRequest = userViewModel::deleteRequest,
                onDeleteAccount = userViewModel::deleteAccount,
                onChangePassword = {
                    startActivity(
                        Intent(this@SecurityActivity, ChangePasswordActivity::class.java)
                    )
                },
                showDeleteDialog = showDeleteDialog,
                showDeleteSuccessDialog = showDeleteSuccessDialog,
                onConfirm = {
                    showDeleteDialog = false
                },
                onDismiss = {
                    showDeleteDialog = false
                },
                snackbar = snackbarHostState,
                onDismissDeleteSuccess = {
                    showDeleteSuccessDialog = false
                    authViewModel.logout()
                    startActivity(
                        Intent(
                            this@SecurityActivity,
                            MainActivity::class.java
                        )
                    )
                    finish()
                }
            )
        }
    }
}