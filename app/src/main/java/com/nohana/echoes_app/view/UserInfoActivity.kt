package com.nohana.echoes_app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nohana.echoes_app.MainActivity
import com.nohana.echoes_app.data.TokenStorage
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.view.screen.LoadingScreen
import com.nohana.echoes_app.view.screen.UserInfo
import com.nohana.echoes_app.view.screen.UserInfoScreen
import com.nohana.echoes_app.view.state.AuthState
import com.nohana.echoes_app.view.state.UserInfoState
import com.nohana.echoes_app.viewmodel.AuthViewModel
import com.nohana.echoes_app.viewmodel.factory.AuthViewModelFactory
import com.nohana.echoes_app.viewmodel.UserViewModel
import com.nohana.echoes_app.viewmodel.factory.UserViewModelFactory

class UserInfoActivity(): ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authViewModel: AuthViewModel by viewModels{
            AuthViewModelFactory(
                NetworkProvider.getAddress(applicationContext),
                applicationContext
            )
        }

        val userViewModel: UserViewModel by viewModels{
            UserViewModelFactory(
                NetworkProvider.getAddress(applicationContext),
                applicationContext
            )
        }


        setContent {
            val userInfoState by userViewModel.userState.collectAsState()

            LaunchedEffect(Unit) {
                authViewModel.validateToken()
            }

            LaunchedEffect(Unit) {
                userViewModel.getUserInfo()
            }


            when(userInfoState) {
                UserInfoState.Loading -> LoadingScreen()
                is UserInfoState.Success -> UserInfoScreen(
                    onLogout = {
                        authViewModel.logout()
                        startActivity(
                            Intent(this@UserInfoActivity, MainActivity::class.java)
                        )
                    },
                    user = (userInfoState as UserInfoState.Success).user
                )
                UserInfoState.Error -> {

                }
            }
        }
    }
}