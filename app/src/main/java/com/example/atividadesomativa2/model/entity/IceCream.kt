package com.example.atividadesomativa2.model.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamContainer

@Entity(
    tableName = "ice_creams",
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("orderId")]
)
data class IceCream (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val orderId: Int,
    val container: IceCreamContainer,
    val quantity: Int
)