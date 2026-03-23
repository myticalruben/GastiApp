package com.ruben.gastiapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ruben.gastiapp.Rutas

data class ItemMenu(val titulo: String, val ruta: String, val icono: ImageVector)

@Composable
fun NavegacionInferior(navController: NavHostController) {
    val items = listOf(
        ItemMenu("Inicio", Rutas.Dashboard.ruta, Icons.Filled.Home),
        ItemMenu("Historial", Rutas.Historial.ruta, Icons.Filled.List),
        ItemMenu("Categorias", Rutas.ConfiguracionCategorias.ruta, Icons.Filled.Settings),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    val mostrarMenu = rutaActual in items.map { it.ruta }

    if (mostrarMenu){
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(item.icono, contentDescription = item.titulo) },
                    label = { Text(item.titulo) },
                    selected = rutaActual == item.ruta,
                    onClick = {
                        navController.navigate(item.ruta){
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}