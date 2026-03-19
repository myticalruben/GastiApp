package com.ruben.gastiapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.ruben.gastiapp.data.local.entity.CategoriaEntity
import com.ruben.gastiapp.data.local.entity.TransaccionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FinanzasDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertarCategoria(categoria: CategoriaEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertarTransaccion(transaccion: TransaccionEntity)

    @Query("SELECT * FROM categorias")
    fun obtenerTodasLasCategorias(): Flow<List<CategoriaEntity>>

    @Query("SELECT * FROM transacciones")
    fun obtenerTodasLasTransacciones(): Flow<List<TransaccionEntity>>

    @Query("Select * FROM transacciones ORDER BY fecha DESC")
    fun obtenerHistorialTransacciones(): Flow<List<TransaccionEntity>>

    @Query("""
        select sum(monto) from transacciones
        inner join categorias on transacciones.categoriaId = categorias.id
        where categorias.tipo = :tipoTransaccion
    """)
    fun obtenerTotalPorTipo(tipoTransaccion:String): Flow<Double?>
}