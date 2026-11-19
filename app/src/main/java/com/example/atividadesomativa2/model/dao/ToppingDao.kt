package com.example.atividadesomativa2.model.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.atividadesomativa2.model.entity.Topping
import kotlinx.coroutines.flow.Flow

@Dao
interface ToppingDao {
    @Query("SELECT * FROM toppings")
    fun getAll(): Flow<List<Topping>>

    @Insert
    suspend fun add(topping: Topping)

    @Update
    suspend fun update(topping: Topping)

    @Delete
    suspend fun delete(topping: Topping)

    @Query("SELECT * FROM toppings WHERE id = :id")
    suspend fun getById(id: Int): Topping?
}