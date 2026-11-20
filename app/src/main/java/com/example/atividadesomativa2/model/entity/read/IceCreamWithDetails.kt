package com.example.atividadesomativa2.model.entity.read

import androidx.room3.Embedded
import androidx.room3.Junction
import androidx.room3.Relation
import com.example.atividadesomativa2.model.entity.IceCream
import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.model.entity.Topping
import com.example.atividadesomativa2.model.entity.crossreference.IceCreamScoopCrossRef
import com.example.atividadesomativa2.model.entity.crossreference.IceCreamToppingCrossRef

data class IceCreamWithDetails(
    @Embedded val iceCream: IceCream,
    @Relation(
        parentColumns = ["id"],
        entityColumns = ["id"],
        associateBy = Junction(
            value = IceCreamScoopCrossRef::class,
            parentColumns = ["iceCreamId"],
            entityColumns = ["scoopId"]
        )
    )
    val scoops: List<Scoop>,
    @Relation(
        parentColumns = ["id"],
        entityColumns = ["id"],
        associateBy = Junction(
            value = IceCreamToppingCrossRef::class,
            parentColumns = ["iceCreamId"],
            entityColumns = ["toppingId"]
        )
    )
    val toppings: List<Topping>
)
