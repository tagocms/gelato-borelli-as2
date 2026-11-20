package com.example.atividadesomativa2.model.entity.read

import androidx.room3.Embedded
import androidx.room3.Relation
import com.example.atividadesomativa2.model.entity.IceCream
import com.example.atividadesomativa2.model.entity.Order

data class OrderWithIceCream(
    @Embedded val order: Order,
    @Relation(
        entity = IceCream::class,
        parentColumns = ["id"],
        entityColumns = ["orderId"]
    )
    val iceCreams: List<IceCreamWithDetails>
)
