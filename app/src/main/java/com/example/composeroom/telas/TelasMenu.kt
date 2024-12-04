package com.example.composeroom.telas

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composeroom.ui.theme.ComposeRoomTheme
import com.example.composeroom.ui.theme.Data.Livro
import com.example.composeroom.ui.theme.Data.LivroDatabase
import kotlinx.coroutines.launch
import kotlin.math.round

//@Preview(showBackground = true)
@Composable
fun IntputNovoLivro() {
    var nome by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = LivroDatabase.getDatabase(context)

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Novo Livro:", fontSize = 16.sp)
        Row(
            modifier = Modifier
               .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome: ") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = ano,
                onValueChange = { ano = it },
                label = { Text("Ano: ") },
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    if (nome.isNotBlank() && ano.isNotBlank()) {
                        val livrosExistentes = db.livroDao().listarLivros()
                        if (livrosExistentes.none { it.nome == nome && it.ano == ano }) {
                            db.livroDao().addLivro(Livro(0, nome, ano))
                            nome = ""
                            ano = ""
                        } else {
                            println("Livro já existe no banco.")
                        }
                    } else {
                        println("Campos não podem estar vazios!")
                    }
                }
            }
        ) {
            Text("Criar Livro")
        }
    }
}

//@Preview(showBackground = true)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TelaMenu(navController: NavController) {

    val context = LocalContext.current
    val db = LivroDatabase.getDatabase(context)
    val coroutineScope = rememberCoroutineScope()
    var livros by remember { mutableStateOf(emptyList<Livro>()) }

    ComposeRoomTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 10.dp,
            border = BorderStroke(0.1.dp, Color.LightGray)

        ) {
            Column {
                IntputNovoLivro()

                Spacer(modifier = Modifier.height(10.dp))

                coroutineScope.launch {
                    livros = db.livroDao().listarLivros()
                }

                ListaDeLivros(livros, navController)

            }

        }
    }

}



@Composable
fun ListaDeLivros(livros: List<Livro>, navController: NavController) {
    LazyColumn {
        items(livros) { livro ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Nome: ${livro.nome}", fontSize = 18.sp)
                    Text("Ano: ${livro.ano}", fontSize = 14.sp, color = Color.Gray)
                }
                IconButton(onClick = {
                    navController.navigate("FormularioEdicaoLivro/${livro.id}")
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar Livro")
                }
            }
        }
    }
}



@Composable
fun CardLivro(livro : Livro, escolherLivro: (String) -> Unit ={} ) {
    Card(
        modifier = Modifier.padding(10.dp).fillMaxWidth().clickable {
            escolherLivro(livro.nome)
        },
        elevation = CardDefaults.cardElevation(4.dp)
    )

    {

        Column(
            modifier = Modifier.padding(6.dp)

        ) {

            Text("ID: ${livro.id}", style = MaterialTheme.typography.bodySmall)
            Text("Nome: ${livro.nome}", style = MaterialTheme.typography.bodyLarge)
            Text("Ano: ${livro.ano} ", style = MaterialTheme.typography.bodyMedium)

        }
    }
}
