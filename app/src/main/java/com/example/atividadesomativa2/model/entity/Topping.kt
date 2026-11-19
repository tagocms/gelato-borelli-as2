package com.example.atividadesomativa2.model.entity

import androidx.compose.ui.graphics.Color
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "toppings")
data class Topping(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val color: Color
)
