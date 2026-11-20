package com.example.atividadesomativa2.model.entity.crossreference

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import com.example.atividadesomativa2.model.entity.IceCream

@Entity(
    tableName = "ice_cream_topping_cross_ref",
    primaryKeys = ["iceCreamId", "toppingId"],
    indices = [Index("toppingId")],
    foreignKeys = [
        ForeignKey(
            entity = IceCream::class,
            parentColumns = ["id"],
            childColumns = ["iceCreamId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IceCreamToppingCrossRef(
    val iceCreamId: Int,
    val toppingId: Int,
    val toppingQuantity: Int
)
