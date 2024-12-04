package com.example.composeroom

import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeroom.telas.FormularioEdicaoLivro
import com.example.composeroom.telas.TelaDetalhes
import com.example.composeroom.telas.TelaMenu
@Composable
fun LivroNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "menu") {
        composable("menu") {
            // Tela principal do menu
            TelaMenu(navController)
        }
        composable("detalhes/{nomeLivro}") { backStackEntry ->
            // Detalhes do livro
            val nomeLivro = backStackEntry.arguments?.getString("nomeLivro")
            if (nomeLivro != null) {
                TelaDetalhes(navController, nomeLivro)
            }
        }
        composable("formularioEdicaoLivro/{livroId}") { backStackEntry ->
            val livroId = backStackEntry.arguments?.getString("livroId")?.toIntOrNull()
            if (livroId != null) {
                FormularioEdicaoLivro(livroId, navController)
            }
        }
    }
}
