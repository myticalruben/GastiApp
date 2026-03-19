package com.ruben.gastiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.ruben.gastiapp.data.local.FinanzasDatabase
import com.ruben.gastiapp.ui.screens.DashboardScreen
import com.ruben.gastiapp.ui.theme.GastiAppTheme
import com.ruben.gastiapp.viewmodel.FinanzasViewModel
import com.ruben.gastiapp.viewmodel.FinanzasViewModelFactory

sealed class Rutas(val ruta: String) {
    object Dashboard : Rutas("dashboard")
    object Historial : Rutas("historial")
    object ConfiguracionCategorias : Rutas("configuracion_categorias")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = Room.databaseBuilder(
            applicationContext,
            FinanzasDatabase::class.java,
            "gastiapp_db"
        ).build()

        val factory = FinanzasViewModelFactory(database.dao)
        setContent {
            GastiAppTheme {
                val navController = rememberNavController()

                val viewModel: FinanzasViewModel = viewModel(factory = factory)
                MainAppStructure(navController, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppStructure(navController: NavHostController, viewModel: FinanzasViewModel) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Text("+")
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Rutas.Dashboard.ruta,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Rutas.Dashboard.ruta) {
                DashboardScreen(viewModel)
            }
            composable(Rutas.Historial.ruta) {
                HistorialScreen()
            }
            composable(Rutas.ConfiguracionCategorias.ruta) {
                CategoriaScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Implement navigation bar here
}

@Composable
fun HistorialScreen() {
    Text("Pantalla: Historial")
}

@Composable
fun CategoriaScreen() {
    Text("Pantalla: Personalizar Categorias")
}
