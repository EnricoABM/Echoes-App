package com.nohana.echoes_app.view.activities.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.activities.home.StudentActivity
import com.nohana.echoes_app.view.activities.home.TeacherActivity
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.components.LoadingScreen
import com.nohana.echoes_app.viewmodel.AuthViewModel
import com.nohana.echoes_app.viewmodel.factory.AuthViewModelFactory

class AuthActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(
            NetworkProvider.getAddress(),
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val state by viewModel.loginState.collectAsState()
            val form by viewModel.form.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.validateToken()
            }

            EchoesAppTheme {
                Column {
                    TitleComponent("Login")

                    when (val s = state) {
                        LoginState.Login -> {
                            LoginScreen(
                                email = form.email.orEmpty(),
                                password = form.password.orEmpty(),
                                onEmailChange = { viewModel.onFormChange(email = it) },
                                onPasswordChange = { viewModel.onFormChange(password = it) },
                                onLogin = { email, password ->
                                    viewModel.login(email, password)
                                }
                            )
                        }

                        LoginState.Loading -> LoadingScreen()

                        is LoginState.Success -> {
                            LaunchedEffect(s) {
                                startActivity(
                                    if (s.role == "TEACHER")
                                        Intent(this@AuthActivity, TeacherActivity::class.java)
                                    else
                                        Intent(this@AuthActivity, StudentActivity::class.java)
                                )
                                finish()
                            }
                        }

                        is LoginState.TwoFactor -> {
                            TwoFactorScreen(
                                email = form.email.orEmpty(),
                                code = form.code.orEmpty(),
                                onCodeChange = { viewModel.onFormChange(code = it) },
                                onTwoFactor = { email, code ->
                                    viewModel.sendTwoFactor(
                                        form.email.orEmpty(),
                                        code
                                    )
                                },
                                error = s.error
                            )
                        }

                        is LoginState.ValidToken -> {
                            LaunchedEffect(s) {
                                startActivity(
                                    if (s.role == "TEACHER")
                                        Intent(this@AuthActivity, TeacherActivity::class.java)
                                    else
                                        Intent(this@AuthActivity, StudentActivity::class.java)
                                )
                                finish()
                            }
                        }

                        is LoginState.Error -> {
                            LoginScreen(
                                email = form.email.orEmpty(),
                                password = form.password.orEmpty(),
                                onEmailChange = { viewModel.onFormChange(email = it) },
                                onPasswordChange = { viewModel.onFormChange(password = it) },
                                onLogin = { email, password ->
                                    viewModel.login(email, password)
                                },
                                error = s.error
                            )
                        }

                        is LoginState.ValidationError -> {
                            LoginScreen(
                                email = form.email.orEmpty(),
                                password = form.password.orEmpty(),
                                onEmailChange = { viewModel.onFormChange(email = it) },
                                onPasswordChange = { viewModel.onFormChange(password = it) },
                                onLogin = { email, password ->
                                    viewModel.login(email, password)
                                },
                                emailError = s.emailError,
                                passwordError = s.passwordError
                            )
                        }
                    }
                }
            }
        }
    }
}