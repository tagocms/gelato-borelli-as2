package com.example.atividadesomativa2.model.entity.auxiliary

enum class OrderStatus {
    PENDING,
    PREPARING,
    READY,
    DELIVERED;

    fun moveToNextStatus() {
        when (this) {
            PENDING -> PREPARING
            PREPARING -> READY
            READY -> DELIVERED
            DELIVERED -> DELIVERED
        }
    }
}