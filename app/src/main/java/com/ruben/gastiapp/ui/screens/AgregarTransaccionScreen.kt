package com.ruben.gastiapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ruben.gastiapp.viewmodel.FinanzasViewModel

@Composable
fun AgregarTransaccionScreen(
    navController: NavController,
    viewModel: FinanzasViewModel,
) {
    var monto by remember { mutableStateOf("") }
    var nota by remember { mutableStateOf("") }
    var tipoSeleccionado by remember { mutableStateOf("GASTO") }

    var tiposTransaccion = listOf("GASTO", "INGRESO", "AHORRO")
    
    var categorias by viewModel.categorias.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Nueva Transacción",
            style = MaterialTheme.typography.headlineMedium
        )
        
    }
}