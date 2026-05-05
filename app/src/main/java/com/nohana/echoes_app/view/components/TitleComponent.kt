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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.nohana.echoes_app.R
import com.nohana.echoes_app.ui.theme.DarkBlue

/**
 * Barra de título padrão do aplicativo, sem botão de voltar.
 *
 * O texto é sempre exibido em uma única linha — se o título for longo,
 * o tamanho da fonte reduz automaticamente até o mínimo de 14sp antes
 * de truncar com reticências.
 *
 * @param text Título exibido na barra.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleComponent(text: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = text,
                fontSize = 22.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = Modifier.background(DarkBlue),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = DarkBlue,
            titleContentColor = Color.White
        )
    )
}

/**
 * Barra de título padrão do aplicativo, com botão de voltar.
 *
 * O texto é sempre exibido em uma única linha — se o título for longo,
 * o tamanho da fonte reduz automaticamente antes de truncar com reticências.
 * O botão de voltar fica alinhado à esquerda, fora da área do título.
 *
 * @param text         Título exibido na barra.
 * @param backFunction Callback invocado ao pressionar o botão de voltar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleComponent(text: String, backFunction: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = text,
                fontSize = 34.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = backFunction) {
                Icon(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = "Icone de Retorno",
                    tint = Color.White
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