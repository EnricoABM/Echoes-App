package com.nohana.echoes_app.view.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nohana.echoes_app.MainActivity
import com.nohana.echoes_app.network.NetworkProvider
import com.nohana.echoes_app.ui.theme.DarkBlue
import com.nohana.echoes_app.view.activities.security.SecurityActivity
import com.nohana.echoes_app.view.activities.settings.PrivacyActivity
import com.nohana.echoes_app.view.components.LoadingScreen
import com.nohana.echoes_app.view.screens.ProfileScreen
import com.nohana.echoes_app.view.activities.settings.SettingsScreen
import com.nohana.echoes_app.view.components.TitleComponent
import com.nohana.echoes_app.view.states.UserDeleteEvent
import com.nohana.echoes_app.viewmodel.AuthViewModel
import com.nohana.echoes_app.viewmodel.UserViewModel
import com.nohana.echoes_app.viewmodel.factory.AuthViewModelFactory
import com.nohana.echoes_app.viewmodel.factory.UserViewModelFactory

/**
 * Activity principal pós-login.
 *
 * Implementa um [Scaffold] com:
 * - **TopBar** centralizada com o título da aba ativa.
 * - **BottomBar** com três abas de navegação:
 *   - [BottomTab.PROFILE]  → ícone de perfil (esquerda)
 *   - [BottomTab.CLASS]  → ícone principal (centro, aba inicial)
 *   - [BottomTab.SETTINGS] → ícone de configurações (direita)
 *
 * O logout chama [AuthViewModel.logout] e redireciona para [MainActivity].
 * A validação de token garante que sessões expiradas retornem ao login.
 */
@OptIn(ExperimentalMaterial3Api::class)
class StudentActivity : ComponentActivity() {

    /**
     * Representa as abas disponíveis na BottomBar.
     *
     * @property label Título exibido na TopBar quando a aba está ativa.
     * @property iconRes Recurso do ícone exibido na BottomBar.
     */
    private enum class BottomTab(val label: String, val iconRes: ImageVector) {
        PROFILE("Perfil", Icons.Rounded.Person),
        CLASS("Turmas", Icons.Rounded.Home),
        SETTINGS("Configurações", Icons.Rounded.Settings)
    }

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(NetworkProvider.getAddress(), applicationContext)
    }

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory(NetworkProvider.getAddress(), applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(Unit) {
                authViewModel.validateToken()
                userViewModel.getUserInfo()
            }

            var selectedTab by rememberSaveable { mutableStateOf(BottomTab.CLASS) }

            Scaffold(

                topBar = {
                    TitleComponent(
                        selectedTab.label
                    )
                },

                bottomBar = {
                    NavigationBar(containerColor = DarkBlue) {

                        // Itens na ordem: Perfil | Dispositivos | Configurações
                        BottomTab.entries.forEach { tab ->
                            NavigationBarItem(
                                selected = selectedTab == tab,
                                onClick = { selectedTab = tab },
                                icon = {
                                    Icon(
                                        modifier = Modifier.size(30.dp),
                                        imageVector = tab.iconRes,
                                        contentDescription = tab.label
                                    )
                                },
                                label = { Text(tab.label, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = DarkBlue,
                                    unselectedIconColor = Color.White.copy(alpha = 0.7f),
                                    selectedTextColor = Color.White,
                                    unselectedTextColor = Color.White.copy(alpha = 0.7f),
                                    indicatorColor = Color.White
                                )
                            )
                        }
                    }
                }

            ) { innerPadding ->

                val uiState by userViewModel.uiState.collectAsState()

                LaunchedEffect(Unit) {
                    userViewModel.event.collect { event ->
                        when (event) {
                            UserDeleteEvent.DeleteAccountSuccess -> {
                                startActivity(
                                    Intent(
                                        this@StudentActivity,
                                        MainActivity::class.java
                                    )
                                )
                                finish()
                            }
                            is UserDeleteEvent.Error -> {
                                // snackbar
                            }
                            is UserDeleteEvent.DeleteRequestSucess -> {

                            }
                        }
                    }
                }

                if (uiState.isLoading && uiState.user == null) {
                    LoadingScreen()
                } else {
                    uiState.user?.let { user ->
                        when (selectedTab) {

                            BottomTab.CLASS -> {
                                HomeScreen()
                            }

                            BottomTab.PROFILE -> {
                                ProfileScreen(
                                    user = user
                                )
                            }

                            BottomTab.SETTINGS -> {
                                SettingsScreen(
                                    onLogout = {
                                        authViewModel.logout()

                                        startActivity(
                                            Intent(this@StudentActivity, MainActivity::class.java)
                                        )

                                        finish()
                                    },
                                    onPrivacy = {
                                        startActivity(
                                            Intent(this@StudentActivity, PrivacyActivity::class.java)
                                        )
                                    },
                                    onSecurity = {
                                        startActivity(
                                            Intent(this@StudentActivity, SecurityActivity::class.java)
                                        )
                                    },
                                    onDeleteAccount = userViewModel::deleteAccount
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}