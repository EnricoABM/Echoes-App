package com.nohana.echoes_app.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Azul = Color(0xFF185FA5)

data class OpcaoMenu(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun TeacherScreen() {

    var pesquisa by remember { mutableStateOf("") }

    var mostrarMenu by remember { mutableStateOf(false) }
    var mostrarCriar by remember { mutableStateOf(false) }
    var mostrarEditar by remember { mutableStateOf(false) }
    var mostrarExcluir by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 95.dp)
            .padding(horizontal = 15.dp)
    ) {

        // ── Barra de Pesquisa ────────────────────────────────────────────────
        OutlinedTextField(
            value = pesquisa,
            onValueChange = { pesquisa = it },
            placeholder = { Text("Pesquisar turma") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Pesquisar",
                    tint = Azul
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 75.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Azul,
                unfocusedBorderColor = Color.LightGray
            )
        )

        // ── Botão Flutuante ─────────────────────────────────────────────────
        FloatingActionButton(
            onClick = { mostrarMenu = true },
            modifier = Modifier.align(Alignment.BottomEnd),
            containerColor = Azul
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Adicionar",
                tint = Color.White
            )
        }
    }

    // ── Modal Menu de Opções ────────────────────────────────────────────────
    if (mostrarMenu) {

        val opcoes = listOf(
            OpcaoMenu("Criar Turma", Icons.Rounded.Person) {
                mostrarMenu = false
                mostrarCriar = true
            },

            OpcaoMenu("Editar Turma", Icons.Rounded.Create) {
                mostrarMenu = false
                mostrarEditar = true
            },

            OpcaoMenu("Excluir Turma", Icons.Rounded.Delete) {
                mostrarMenu = false
                mostrarExcluir = true
            }
        )

        AlertDialog(
            onDismissRequest = { mostrarMenu = false },

            title = {
                Text(
                    text = "Opções de Turma",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Azul
                )
            },

            text = {
                Column {

                    opcoes.forEachIndexed { index, opcao ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { opcao.onClick() }
                                .padding(vertical = 12.dp),

                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = opcao.icon,
                                contentDescription = opcao.label,
                                tint = Azul
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = opcao.label,
                                fontSize = 16.sp,
                                color = Color.DarkGray
                            )
                        }

                        if (index < opcoes.lastIndex) {
                            HorizontalDivider(color = Color.LightGray)
                        }
                    }
                }
            },

            confirmButton = {},

            dismissButton = {
                TextButton(
                    onClick = { mostrarMenu = false }
                ) {
                    Text("Fechar", color = Azul)
                }
            }
        )
    }

    // ── Modal Criar Turma ───────────────────────────────────────────────────
    if (mostrarCriar) {

        var nome by remember { mutableStateOf("") }
        var descricao by remember { mutableStateOf("") }
        var erro by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { mostrarCriar = false },

            title = {
                Text(
                    text = "Criar Turma",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Azul
                )
            },

            text = {

                Column {

                    Text(
                        "Nome",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        placeholder = { Text("Ex: Turma A") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Azul,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Descrição",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = descricao,
                        onValueChange = { descricao = it },

                        placeholder = {
                            Text("Ex: Turma do 3º ano")
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),

                        maxLines = 4,

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Azul,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    if (erro) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Preencha todos os campos!",
                            color = Color.Red,
                            fontSize = 13.sp
                        )
                    }
                }
            },

            confirmButton = {

                Button(
                    onClick = {

                        if (
                            nome.isBlank() ||
                            descricao.isBlank()
                        ) {
                            erro = true

                        } else {

                            // Lógica de criar turma aqui
                            mostrarCriar = false
                        }
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Azul
                    )
                ) {
                    Text("Criar", color = Color.White)
                }
            },

            dismissButton = {

                TextButton(
                    onClick = { mostrarCriar = false }
                ) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        )
    }
}