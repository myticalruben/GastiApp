package com.ruben.gastiapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ruben.gastiapp.viewmodel.FinanzasViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistorialScreen(viewModel: FinanzasViewModel) {
    val historial by viewModel.historial.collectAsState()
    val categorias by viewModel.categorias.collectAsState()

    val formateadorFecha = SimpleDateFormat("dd MM yyy, HH:mm", Locale.getDefault())

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Historial de Movimientos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        if (historial.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aún no tienes movimientos registrados.", color = androidx.compose.ui.graphics.Color.Gray)
            }
        }else{
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(historial){transaccion ->
                    val categoria = categorias.find { it.id == transaccion.categoriaId }
                    val esGastos = categoria?.tipo == "GASTOS"
                    val colorMonto = if (esGastos) MaterialTheme.colorScheme.error
                                     else androidx.compose.ui.graphics.Color(0xFF4CAF50)
                    val signo = if(esGastos) "-" else "+"

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(androidx.compose.ui.graphics.Color(categoria?.color ?: android.graphics.Color.GRAY))
                            )
                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = categoria?.nombre ?: "Categoria eliminada",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                if (transaccion.nota.isNotBlank()){
                                    Text(
                                        text = transaccion.nota,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                    )
                                }
                                Text(
                                    text = formateadorFecha.format(Date(transaccion.fecha)),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = androidx.compose.ui.graphics.Color.Gray
                                )
                            }

                            Text(
                                text = "$signo$${String.format("%.2f", transaccion.monto)}",
                                style = MaterialTheme.typography.titleMedium,
                                color = colorMonto,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}