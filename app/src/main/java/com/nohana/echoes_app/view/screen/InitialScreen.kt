package com.nohana.echoes_app.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.nohana.echoes_app.R
import com.nohana.echoes_app.ui.theme.Blue
import com.nohana.echoes_app.ui.theme.DarkBlue
import com.nohana.echoes_app.ui.theme.EchoesAppTheme

@Composable
fun InitialScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onServer: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(DarkBlue)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.vet_icon_white),
                    contentDescription = "Vet Icon"
                )
                Text(
                    text = "Echoes",
                    fontSize = 14.em,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }


            Column(
                modifier = Modifier.width(200.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Blue
                    ),
                    onClick = onLogin
                ) {
                    Text(
                        text = "Entrar",
                        fontSize = 4.em
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Blue
                    ),
                    onClick = onRegister
                ) {
                    Text(
                        text = "Registrar",
                        fontSize = 4.em
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Blue
                    ),
                    onClick = onServer
                ) {
                    Text(
                        text = "IP Config",
                        fontSize = 4.em
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ViewInitialScreen() {
    EchoesAppTheme() {
        InitialScreen(
            onLogin = {},
            onRegister = {},
            onServer = {}
        )
    }
}
