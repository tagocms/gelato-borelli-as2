package com.example.atividadesomativa2.model.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val iceCreams: List<IceCream>
)