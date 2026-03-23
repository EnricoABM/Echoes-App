package com.nohana.echoes_app.view.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nohana.echoes_app.R
import com.nohana.echoes_app.model.User
import com.nohana.echoes_app.ui.theme.DarkBlue
import com.nohana.echoes_app.ui.theme.EchoesAppTheme
import com.nohana.echoes_app.view.ChangePasswordActivity
import com.nohana.echoes_app.view.components.TitleComponent

@Composable
fun UserInfoScreen(
    onLogout: () -> Unit,
    user: User
) {
    val context = LocalContext.current

    TitleComponent("Perfil")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        UserInfo(user)

        Button(
            onClick = {
                context.startActivity(
                    Intent(context, ChangePasswordActivity::class.java)
                )
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = DarkBlue,
            )
        ) {
            Text("Alterar a Senha")
        }

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = DarkBlue,
            )
        ) {
            Text("Sair")
        }
    }
}

@Composable
fun UserInfo(user: User) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(180.dp)
                .padding(10.dp)
                .background(Color.LightGray, CircleShape),
            painter = painterResource(R.drawable.person_image),
            contentDescription = "Imagem de Perfil"
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Nome")
            TextField(
                onValueChange = { },
                readOnly = true,
                value = user.name
            )

            Text("E-mail")
            TextField(
                onValueChange = { },
                readOnly = true,
                value = user.email
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewUserInfoScreen() {
    EchoesAppTheme() {
        UserInfoScreen(
            { },
            User("Enrico", "enrico@test.com"))
    }
}