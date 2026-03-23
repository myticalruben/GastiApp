package com.ruben.gastiapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ruben.gastiapp.Rutas
import com.ruben.gastiapp.viewmodel.FinanzasViewModel

@Composable
fun DashboardScreen(viewModel: FinanzasViewModel, navController: NavHostController) {
    val ingresos by viewModel.totalIngresos.collectAsState()
    val gastos by viewModel.totalGastos.collectAsState()
    val ahorros by viewModel.totalAhorros.collectAsState()

    val totalIngresosSafe = ingresos ?: 0.0
    val totalGastosSafe = gastos ?: 0.0
    val totalAhorrosSafe = ahorros ?: 0.0

    val balanceDisponible = totalIngresosSafe - (totalGastosSafe + totalAhorrosSafe)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        // --- SECCIÓN 1: SALDO PRINCIPAL ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Saldo Disponible",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "$${String.format("%.2f", balanceDisponible)}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        "Total Ingresos - (Gastos + Ahorros)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // --- SECCIÓN 2: TARJETAS DE RESUMEN (Ingresos, Gastos, Ahorros apartado) ---
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    titulo = "Ingresos",
                    monto = totalIngresosSafe,
                    colorMonto = Color(0xFF4CAF50)
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    titulo = "Gastos",
                    monto = totalGastosSafe,
                    colorMonto = MaterialTheme.colorScheme.error
                )
            }
        }

        item {
            StatCard(
                modifier = Modifier.fillMaxWidth(),
                titulo = "Ahorros Acumulado",
                monto = totalAhorrosSafe,
                colorMonto = Color(0xFF00ACC1)
            )
        }

        // --- SECCIÓN 3: PENDIENTES ---
        // Aquí podrías agregar gráficas más adelante o la actividad reciente.
        item{
            Spacer(modifier = Modifier.height(24.dp))
            Divider()
            Text(
                text = "Actividad Reciente",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.Gray
            )
            Text(
                text = "Aquí se mostrarán las últimas transacciones",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )
        }
    }

}

// 4. COMPONENTE REUTILIZABLE PARA TARJETAS PEQUEÑAS
@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    titulo: String,
    monto: Double,
    colorMonto: Color
){
    ElevatedCard(modifier) {
        Column(modifier=  Modifier.padding(16.dp)) {
            Text(titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "$${String.format("%.2f", monto)}",
                style = MaterialTheme.typography.titleLarge,
                color = colorMonto,
                fontWeight = FontWeight.Bold
            )
        }
    }
}