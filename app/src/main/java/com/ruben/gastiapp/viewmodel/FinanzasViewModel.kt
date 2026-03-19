package com.ruben.gastiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruben.gastiapp.data.local.dao.FinanzasDao
import com.ruben.gastiapp.data.local.entity.CategoriaEntity
import com.ruben.gastiapp.data.local.entity.TransaccionEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FinanzasViewModel(private val dao: FinanzasDao): ViewModel() {

    val categorias: StateFlow<List<CategoriaEntity>> = dao.obtenerTodasLasCategorias()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val historial: StateFlow<List<TransaccionEntity>> = dao.obtenerHistorialTransacciones()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalIngresos: StateFlow<Double?> = dao.obtenerTotalPorTipo("INGRESO")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalGastos: StateFlow<Double?> = dao.obtenerTotalPorTipo("GASTOS")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalAhorros: StateFlow<Double?> = dao.obtenerTotalPorTipo("AHORRO")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)



    fun agregarCategoria(nombre: String, icono: String, color: Int, tipo: String){
        viewModelScope.launch {
           val nuevaCategoria = CategoriaEntity(
               nombre = nombre,
               icono = icono,
               color = color,
               tipo = tipo
           )
            dao.insertarCategoria(nuevaCategoria)
        }
    }

    fun agregarTransaccion(monto: Double, categoriaId: String, nota: String){
        viewModelScope.launch {
            val nuevaTransaccion = TransaccionEntity(
                monto = monto,
                categoriaId = categoriaId,
                nota = nota
            )
            dao.insertarTransaccion(nuevaTransaccion)
        }
    }
}