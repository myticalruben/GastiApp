package com.ruben.gastiapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "categorias")
data class CategoriaEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val color: Int,
    val icono: String,
    val tipo: String
)
