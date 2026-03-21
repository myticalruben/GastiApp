package com.ruben.gastiapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
    
    //var categorias by viewModel.categorias.collectAsState()


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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            tiposTransaccion.forEach { tipo ->
                FilterChip(
                    selected = (tipo == tipoSeleccionado),
                    onClick = { tipoSeleccionado = tipo },
                    label = { Text(tipo) }
                )
            }
        }

        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it},
            label = { Text("Monto ($)")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = nota,
            onValueChange = { nota = it},
            label = { Text("Descripción (Ej. Cena con amigos)")},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val montoDouble = monto.toDoubleOrNull() ?: 0.0

                if (montoDouble > 0){
                    viewModel.agregarTransaccion(
                        monto = montoDouble,
                        categoriaId = "cat_1",
                        nota = nota
                    )

                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = monto.isNotBlank()
        ){
            Text("Guardar Transacción")
        }
    }
}