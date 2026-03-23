package com.ruben.gastiapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ruben.gastiapp.viewmodel.FinanzasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriasScreen(navController: NavController, viewModel: FinanzasViewModel) {
    val categorias by viewModel.categorias.collectAsState()

    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {mostrarDialogo = true}) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Categoría")
            }
        }
    ) { paddingValues ->
        if (categorias.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ){
                Text("No tienes categorías. ¡Crea tu primera categoría abajo!")
            }
        }else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categorias) { categoria ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color(categoria.color))
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = categoria.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(text = categoria.tipo, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }

                            IconButton(onClick = {
                                viewModel.eliminarCategoria(categoria)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Eliminar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }

        if(mostrarDialogo){
            DialogoNuevaCategoria(
                onDismiss = {mostrarDialogo = false},
                onGuardar = { nombre, tipo, colorInt ->
                    viewModel.agregarCategoria(
                        nombre = nombre,
                        icono = "icono_por_defecto",
                        color = colorInt,
                        tipo = tipo
                    )
                    mostrarDialogo = false
                }
            )
        }
    }
}


@Composable
fun DialogoNuevaCategoria(onDismiss: () -> Unit, onGuardar: (String, String, Int) -> Unit) {
    var nombre by remember { mutableStateOf("")}
    var tipoSeleccionado by remember {mutableStateOf("GASTOS")}
    val tipos = listOf("GASTOS", "INGRESOS", "AHORROS")

    val colores = listOf(
        0xFFF44336.toInt(), // Rojo
        0xFF4CAF50.toInt(), // Verde
        0xFF2196F3.toInt(), // Azul
        0xFFFF9800.toInt(), // Naranja
        0xFF9C27B0.toInt()  // Morado
    )
    var colorSeleccionado by remember {mutableStateOf(colores[0])}

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Nueva Categoria")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it},
                    label = { Text("Nombre (Ej. Comida")},
                    singleLine = true
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    tipos.forEach { tipo ->
                        FilterChip(
                            selected = tipo == tipoSeleccionado,
                            onClick =  { tipoSeleccionado = tipo},
                            label = { Text(tipo)}
                        )
                    }
                }

                Text("Color:")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    colores.forEach { colorInt ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(colorInt))
                                .clickable { colorSeleccionado = colorInt}
                        ){
                            if (colorSeleccionado == colorInt){
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nombre.isNotBlank()){
                        onGuardar(nombre, tipoSeleccionado, colorSeleccionado)
                    }
                }
            ) { Text("Guardar")}
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar")}
        }
    )
}