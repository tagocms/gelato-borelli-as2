package com.example.atividadesomativa2.model.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Update
import com.example.atividadesomativa2.model.entity.Order
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamInput
import com.example.atividadesomativa2.model.entity.auxiliary.OrderStatus
import com.example.atividadesomativa2.model.entity.read.OrderWithIceCream
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface OrderDao {
    @Transaction
    @Query("SELECT * FROM orders")
    fun getAll(): Flow<List<OrderWithIceCream>>

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getById(id: Int): OrderWithIceCream?

    @Insert
    suspend fun add(order: Order): Long

    @Transaction
    @Query("UPDATE orders SET status = :newStatus, lastUpdatedAt = :lastUpdatedAt WHERE id = :id")
    suspend fun updateOrderStatus(id: Int, newStatus: OrderStatus, lastUpdatedAt: LocalDateTime = LocalDateTime.now())

    @Transaction
    @Query("UPDATE orders SET lastUpdatedAt = :lastUpdatedAt WHERE id = :id")
    suspend fun updateLastUpdatedAt(id: Int, lastUpdatedAt: LocalDateTime = LocalDateTime.now())

    @Delete
    suspend fun delete(order: Order)
}