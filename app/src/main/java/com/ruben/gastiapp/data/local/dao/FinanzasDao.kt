package com.ruben.gastiapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.ruben.gastiapp.data.local.entity.CategoriaEntity
import com.ruben.gastiapp.data.local.entity.TransaccionEntity
import kotlinx.coroutines.flow.Flow

data class TotalesGastoCategoria(
    val nombre: String,
    val montoTotal: Double,
    val color: Int
)

@Dao
interface FinanzasDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertarCategoria(categoria: CategoriaEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertarTransaccion(transaccion: TransaccionEntity)

    @Delete
    suspend fun eliminarCategoria(categoria: CategoriaEntity)

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

    @Query("""
        select tc.nombre, sum(tt.monto) as montoTotal, tc.color
        from transacciones tt
        inner join categorias tc on tt.categoriaId = tc.id
        where tc.tipo = 'GASTOS'
        group by tc.id
    """)
    fun obtenerTotalesGastoPorCategoria(): Flow<List<TotalesGastoCategoria>>
}