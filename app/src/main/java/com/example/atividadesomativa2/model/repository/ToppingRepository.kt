package com.example.atividadesomativa2.model.repository

import androidx.compose.ui.graphics.Color
import com.example.atividadesomativa2.model.dao.ToppingDao
import com.example.atividadesomativa2.model.entity.Topping
import kotlinx.coroutines.flow.Flow

class ToppingRepository(
    private val toppingDao: ToppingDao
) {
    fun getAll(): Flow<List<Topping>> {
        return toppingDao.getAll()
    }

    suspend fun add(name: String, color: Color) {
        toppingDao.add(
            Topping(name = name, color = color)
        )
    }

    suspend fun update(id: Int, name: String, color: Color) {
        toppingDao.update(
            Topping(id = id, name = name, color = color)
        )
    }

    suspend fun delete(topping: Topping) {
        toppingDao.delete(topping)
    }

    suspend fun getById(id: Int): Topping? {
        return toppingDao.getById(id)
    }
}