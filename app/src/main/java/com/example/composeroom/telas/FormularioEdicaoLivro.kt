package com.example.composeroom.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composeroom.ui.theme.Data.Livro
import com.example.composeroom.ui.theme.Data.LivroDatabase
import kotlinx.coroutines.launch

@Composable
fun FormularioEdicaoLivro(
    livroId: Int,
    navController: NavController
) {
    // Carregar o banco de dados e o livro correspondente
    val context = LocalContext.current
    val db = LivroDatabase.getDatabase(context)
    val coroutineScope = rememberCoroutineScope()

    // Estado para armazenar os dados do livro
    var livro by remember { mutableStateOf<Livro?>(null) }

    // Carregar os dados do livro com base no ID
    LaunchedEffect(livroId) {
        coroutineScope.launch {
            livro = db.livroDao().getLivroById(livroId)
        }
    }

    // Exibir o formulário apenas se o livro for carregado
    livro?.let {
        var nome by remember { mutableStateOf(it.nome) }
        var ano by remember { mutableStateOf(it.ano) }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Editar Livro:", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ano,
                onValueChange = { ano = it },
                label = { Text("Ano") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            // Atualizar o livro no banco de dados
                            db.livroDao().atualizarLivro(it.copy(nome = nome, ano = ano))
                            // Voltar para a tela anterior
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Salvar")
                }
                Button(onClick = { navController.popBackStack() }) {
                    Text("Cancelar")
                }
            }
        }
    } ?: run {
        // Mostrar um estado de carregamento ou mensagem de erro se o livro for nulo
        Text(
            text = "Carregando informações do livro...",
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp
        )
    }
}
