package com.example.atividadesomativa2.model.repository

import androidx.compose.ui.graphics.Color
import com.example.atividadesomativa2.model.dao.ScoopDao
import com.example.atividadesomativa2.model.entity.Scoop
import kotlinx.coroutines.flow.Flow

class ScoopRepository(
    private val scoopDao: ScoopDao
) {
    fun getAll(): Flow<List<Scoop>> {
        return scoopDao.getAll()
    }

    suspend fun add(flavor: String, color: Color) {
        scoopDao.add(
            Scoop(flavor = flavor, color = color)
        )
    }

    suspend fun update(id: Int, flavor: String, color: Color) {
        scoopDao.update(
            Scoop(id = id, flavor = flavor, color = color)
        )
    }

    suspend fun delete(scoop: Scoop) {
        scoopDao.delete(scoop)
    }

    suspend fun getById(id: Int): Scoop? {
        return scoopDao.getById(id)
    }
}