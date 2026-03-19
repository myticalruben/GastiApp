package com.ruben.gastiapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ruben.gastiapp.data.local.dao.FinanzasDao

class FinanzasViewModelFactory(private val dao: FinanzasDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinanzasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FinanzasViewModel(dao) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}