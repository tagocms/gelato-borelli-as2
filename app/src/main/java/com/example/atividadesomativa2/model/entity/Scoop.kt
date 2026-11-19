package com.example.atividadesomativa2.model.entity

import androidx.compose.ui.graphics.Color
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "scoops")
data class Scoop(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val flavor: String,
    val color: Color
)