package com.example.atividadesomativa2.model.entity.auxiliary

import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.model.entity.Topping

data class DraftIceCream(
    val id: Int = 0,
    val container: IceCreamContainer,
    val quantity: Int,
    val scoops: List<Scoop>,
    val toppings: List<Topping>
)
