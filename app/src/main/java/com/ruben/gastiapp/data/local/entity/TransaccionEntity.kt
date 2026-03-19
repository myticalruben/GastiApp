package com.ruben.gastiapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "transacciones")
data class TransaccionEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val monto: Double,
    val categoriaId: String,
    val nota: String,
    val fecha: Long = System.currentTimeMillis()
)

