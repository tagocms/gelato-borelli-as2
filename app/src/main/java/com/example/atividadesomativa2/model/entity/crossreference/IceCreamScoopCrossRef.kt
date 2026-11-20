package com.example.atividadesomativa2.model.entity.crossreference

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import com.example.atividadesomativa2.model.entity.IceCream

@Entity(
    tableName = "ice_cream_scoop_cross_ref",
    primaryKeys = ["iceCreamId", "scoopId"],
    indices = [Index("scoopId")],
    foreignKeys = [
        ForeignKey(
            entity = IceCream::class,
            parentColumns = ["id"],
            childColumns = ["iceCreamId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IceCreamScoopCrossRef(
    val iceCreamId: Int,
    val scoopId: Int,
    val scoopQuantity: Int
)
