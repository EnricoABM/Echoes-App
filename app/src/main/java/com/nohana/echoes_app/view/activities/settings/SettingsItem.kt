package com.nohana.echoes_app.view.activities.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nohana.echoes_app.ui.theme.EchoesAppTheme

/**
 * Componente para opçoes da tela de configuraçoes
 *
 * Define os elementos visuais presentes na tela e a açao realizada apos
 * clicar na opçao
 *
 * [title] Titulo apresentado na opcao
 * [icon] Icone adicionado a opçao
 * [onClick] Açao a ser realizada ao clicar na opcao
 * */
@Composable
fun SettingsItem(
    title: String,
    textColor: Color = Color.Black,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 18.dp, horizontal = 20.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            icon()

            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = textColor
        )
    }

    HorizontalDivider()
}

@Preview
@Composable
fun SettingsRowPreview() {
    EchoesAppTheme() {
        SettingsItem(
            title = "Teste",
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            onClick = { }
        )
    }
}
