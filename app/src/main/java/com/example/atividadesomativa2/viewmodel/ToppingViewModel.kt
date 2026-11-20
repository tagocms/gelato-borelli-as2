package com.example.atividadesomativa2.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividadesomativa2.model.database.AppDatabase
import com.example.atividadesomativa2.model.entity.Topping
import com.example.atividadesomativa2.model.repository.ToppingRepository
import kotlinx.coroutines.launch

class ToppingViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ToppingRepository

    var toppings = mutableStateListOf<Topping>()
        private set

    init {
        val dao = AppDatabase.getDatabase(application).toppingDao()
        repository = ToppingRepository(dao)
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                toppings.clear()
                toppings.addAll(list)
            }
        }
    }

    fun canSave(name: String?, color: Color?): Boolean {
        return isNameValid(name) && isColorValid(color)
    }

    fun isNameValid(name: String?): Boolean {
        return !name.isNullOrBlank()
    }

    fun isColorValid(color: Color?): Boolean {
        return color != null
    }

    fun add(name: String, color: Color) {
        if (!canSave(name, color)) return

        viewModelScope.launch {
            repository.add(name, color)
        }
    }

    fun delete(topping: Topping) {
        viewModelScope.launch {
            repository.delete(topping)
        }
    }

    fun update(id: Int, name: String, color: Color) {
        if (!canSave(name, color)) return

        viewModelScope.launch {
            repository.update(id, name, color)
        }
    }

    private fun getById(id: Int): Topping? {
        return toppings.find { it.id == id }
    }
}