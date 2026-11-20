package com.example.atividadesomativa2.model.entity.auxiliary


data class IceCreamInput(
    val container: IceCreamContainer,
    val scoopIdsAndQuantities: Map<Int, Int>,
    val toppingIdsAndQuantities: Map<Int, Int>,
    val quantity: Int
)
