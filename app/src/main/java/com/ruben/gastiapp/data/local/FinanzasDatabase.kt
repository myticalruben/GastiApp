package com.ruben.gastiapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ruben.gastiapp.data.local.dao.FinanzasDao
import com.ruben.gastiapp.data.local.entity.CategoriaEntity
import com.ruben.gastiapp.data.local.entity.TransaccionEntity

@Database(
    entities = [CategoriaEntity::class, TransaccionEntity::class],
    version = 1,
    exportSchema = false

)
abstract class FinanzasDatabase: RoomDatabase() {
    abstract val dao: FinanzasDao

}