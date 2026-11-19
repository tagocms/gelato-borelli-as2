package com.example.atividadesomativa2.model.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.atividadesomativa2.model.entity.Scoop
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoopDao {
    @Query("SELECT * FROM scoops")
    fun getAll(): Flow<List<Scoop>>

    @Insert
    suspend fun add(scoop: Scoop)

    @Update
    suspend fun update(scoop: Scoop)

    @Delete
    suspend fun delete(scoop: Scoop)

    @Query("SELECT * FROM scoops WHERE id = :id")
    suspend fun getById(id: Int): Scoop?
}