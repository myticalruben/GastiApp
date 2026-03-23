package com.ruben.gastiapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ruben.gastiapp.data.local.dao.TotalesGastoCategoria

@Composable
fun GraficaGastosPorCategoria(
    datos: List<TotalesGastoCategoria>,
    size: Dp = 200.dp,
    grosorTrazo: Dp = 30.dp
) {
    val montoTotalGastos = datos.sumOf { it.montoTotal }

    if(datos.isEmpty()){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(size)
        ){
            Canvas(modifier = Modifier.size(size)) {
                drawArc(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = grosorTrazo.toPx())
                )
            }
            Text("No hay gastos", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        return
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size)
    ){
        Canvas(modifier = Modifier.size(size)) {
            var anguloInicio = -90f

            datos.forEach { categoria ->
                val sweepAngle = (categoria.montoTotal.toFloat() / montoTotalGastos.toFloat()) * 360f

                drawArc(
                    color = Color(categoria.color),
                    startAngle = anguloInicio,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = grosorTrazo.toPx())
                )

                anguloInicio += sweepAngle
            }
        }

        Text(
            text = "En qué gastas",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}