package com.example.atividadesomativa2.model.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.atividadesomativa2.model.entity.auxiliary.OrderStatus
import java.time.LocalDateTime

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val status: OrderStatus,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var lastUpdatedAt: LocalDateTime = LocalDateTime.now()
)