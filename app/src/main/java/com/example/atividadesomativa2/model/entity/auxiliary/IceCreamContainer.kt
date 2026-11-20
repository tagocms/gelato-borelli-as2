package com.example.atividadesomativa2.model.entity.auxiliary

enum class IceCreamContainer {
    CUP, CESTINO, CONE;

    fun getScoops(): Int {
        return when (this) {
            CUP -> 3
            CESTINO -> 4
            CONE -> 2
        }
    }

    fun getToppings(): Int {
        return when (this) {
            CUP -> 2
            CESTINO -> 3
            CONE -> 1
        }
    }
}