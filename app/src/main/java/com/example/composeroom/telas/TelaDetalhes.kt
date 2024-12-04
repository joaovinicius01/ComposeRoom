package com.example.composeroom.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeroom.ui.theme.Data.Livro

//@preview
@Composable
fun TelaDetalhes(navController: NavHostController, nomeLivro: String) {
    Surface (modifier = Modifier.fillMaxSize()) {
    Column {
        Text("Livro: $nomeLivro")
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("<-voltar")
        }
        }
    }
}

