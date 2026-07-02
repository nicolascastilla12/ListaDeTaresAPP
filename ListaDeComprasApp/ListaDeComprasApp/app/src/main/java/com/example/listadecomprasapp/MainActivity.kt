package com.example.listadecomprasapp

import androidx.activity.viewModels
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listadecomprasapp.ui.theme.ListaDeComprasAppTheme // Asegúrate que el nombre del tema sea correcto

class MainActivity : ComponentActivity() {

    // Obtenemos el ViewModel usando el Factory
    private val ShoppingViewModel: ShoppingViewModel by this.viewModels {
        ShoppingViewModelFactory((application as ShoppingApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaDeComprasAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Función principal que controla la navegación
                    AppNavigation(viewModel = ShoppingViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: ShoppingViewModel) {
    val navController = rememberNavController()

    // NavHost define las "rutas" de la app
    NavHost(navController = navController, startDestination = "login") {

        // Ruta para la pantalla de Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Al hacer login, navegamos a la lista
                    navController.navigate("shoppingList") {
                        // Borramos el login del historial para no poder volver
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Ruta para la pantalla de la Lista de Compras
        composable("shoppingList") {
            ShoppingListScreen(viewModel = viewModel)
        }
    }
}