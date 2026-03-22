package com.ruben.gastiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.ruben.gastiapp.data.local.FinanzasDatabase
import com.ruben.gastiapp.ui.screens.AgregarTransaccionScreen
import com.ruben.gastiapp.ui.screens.CategoriasScreen
import com.ruben.gastiapp.ui.screens.DashboardScreen
import com.ruben.gastiapp.ui.theme.GastiAppTheme
import com.ruben.gastiapp.viewmodel.FinanzasViewModel
import com.ruben.gastiapp.viewmodel.FinanzasViewModelFactory

sealed class Rutas(val ruta: String) {
    object Dashboard : Rutas("dashboard")
    object Historial : Rutas("historial")
    object ConfiguracionCategorias : Rutas("configuracion_categorias")

    object AgregarTransaccion: Rutas("agregar_transaccion")
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    Scaffold(
        //bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            if (rutaActual == Rutas.Dashboard.ruta){
                FloatingActionButton(onClick = {
                    navController.navigate(Rutas.AgregarTransaccion.ruta)
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Añadir Transaccion")
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Rutas.Dashboard.ruta,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Rutas.Dashboard.ruta) {
                DashboardScreen(viewModel, navController)
            }
            composable(Rutas.Historial.ruta) {
                HistorialScreen()
            }
            composable(Rutas.ConfiguracionCategorias.ruta) {
                CategoriasScreen(navController, viewModel)
            }
            composable(Rutas.AgregarTransaccion.ruta) {
                AgregarTransaccionScreen(navController, viewModel)
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
