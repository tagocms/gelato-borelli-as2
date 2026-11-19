package com.example.atividadesomativa2.model.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamContainer

@Entity(tableName = "ice_creams")
data class IceCream (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val scoops: List<Scoop>,
    val toppings: List<Topping>,
    val container: IceCreamContainer
)