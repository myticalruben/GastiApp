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
import com.ruben.gastiapp.data.local.entity.CategoriaEntity
import com.ruben.gastiapp.viewmodel.FinanzasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarTransaccionScreen(
    navController: NavController,
    viewModel: FinanzasViewModel,
) {
    var monto by remember { mutableStateOf("") }
    var nota by remember { mutableStateOf("") }
    var tipoSeleccionado by remember { mutableStateOf("GASTOS") }

    var tiposTransaccion = listOf("GASTOS", "INGRESOS", "AHORROS")

    val todasLascategorias by viewModel.categorias.collectAsState()

    val categoriasFiltradas = todasLascategorias.filter { it.tipo == tipoSeleccionado }

    var menuExpandido by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf<CategoriaEntity?>(null) }

    LaunchedEffect(tipoSeleccionado) {
        categoriaSeleccionada = null
    }

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

        ExposedDropdownMenuBox(
            expanded = menuExpandido,
            onExpandedChange = { menuExpandido = !menuExpandido}
        ) {
            OutlinedTextField(
                value = categoriaSeleccionada?.nombre ?: "Selecciona una categoria... ",
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoria")},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpandido)},
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = menuExpandido,
                onDismissRequest = { menuExpandido = false}
            ) {
                if (categoriasFiltradas.isEmpty()){
                    DropdownMenuItem(
                        text = { Text("No hay categorias creadas para este tipo")},
                        onClick = { menuExpandido = false}
                    )
                }else{
                    categoriasFiltradas.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria.nombre)},
                            onClick = {
                                categoriaSeleccionada = categoria
                                menuExpandido = false
                            }
                        )
                    }
                }
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

                if (montoDouble > 0 && categoriaSeleccionada != null){
                    viewModel.agregarTransaccion(
                        monto = montoDouble,
                        categoriaId = categoriaSeleccionada!!.id,
                        nota = nota
                    )

                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = monto.isNotBlank() && categoriaSeleccionada != null
        ){
            Text("Guardar Transacción")
        }
    }
}