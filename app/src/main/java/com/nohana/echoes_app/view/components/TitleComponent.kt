package com.nohana.echoes_app.view.components

import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.em
import com.nohana.echoes_app.R
import com.nohana.echoes_app.ui.theme.DarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleComponent(
    text: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text=text,
                fontSize = 8.em
            )
        },
        modifier = Modifier.background(DarkBlue),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkBlue,
            titleContentColor = Color.White
        )

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleComponent(
    text: String,
    backFunction: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text=text,
                fontSize = 10.em
            )
        },
        navigationIcon = {
            IconButton(
                onClick = backFunction
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = "Icone de Retorno"
                )
            }
        },
        modifier = Modifier.background(DarkBlue),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkBlue,
            titleContentColor = Color.White
        )

    )
}