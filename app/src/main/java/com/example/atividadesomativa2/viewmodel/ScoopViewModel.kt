package com.example.atividadesomativa2.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividadesomativa2.model.database.AppDatabase
import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.model.repository.ScoopRepository
import kotlinx.coroutines.launch

class ScoopViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ScoopRepository

    var scoops = mutableStateListOf<Scoop>()
        private set

    init {
        val dao = AppDatabase.getDatabase(application).scoopDao()
        repository = ScoopRepository(dao)
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                scoops.clear()
                scoops.addAll(list)
            }
        }
    }

    fun canSave(flavor: String?, color: Color?): Boolean {
        return isFlavorValid(flavor) && isColorValid(color)
    }

    fun isFlavorValid(flavor: String?): Boolean {
        return !flavor.isNullOrBlank()
    }

    fun isColorValid(color: Color?): Boolean {
        return color != null
    }

    fun add(flavor: String, color: Color) {
        if (!canSave(flavor, color)) return

        viewModelScope.launch {
            repository.add(flavor, color)
        }
    }

    fun delete(scoop: Scoop) {
        viewModelScope.launch {
            repository.delete(scoop)
        }
    }

    fun update(id: Int, flavor: String, color: Color) {
        if (!canSave(flavor, color)) return

        viewModelScope.launch {
            repository.update(id, flavor, color)
        }
    }

    fun getById(id: Int): Scoop? {
        return scoops.find { it.id == id }
    }
}