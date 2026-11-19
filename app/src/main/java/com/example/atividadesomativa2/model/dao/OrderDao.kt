package com.example.atividadesomativa2.model.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.atividadesomativa2.model.entity.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders")
    fun getAll(): Flow<List<Order>>

    @Insert
    suspend fun add(order: Order)

    @Update
    suspend fun update(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getById(id: Int): Order?
}