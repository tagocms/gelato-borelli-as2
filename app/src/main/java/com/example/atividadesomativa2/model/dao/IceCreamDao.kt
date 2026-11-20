package com.example.atividadesomativa2.model.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Update
import com.example.atividadesomativa2.model.entity.IceCream
import com.example.atividadesomativa2.model.entity.Order
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamInput
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamUpdateInput
import com.example.atividadesomativa2.model.entity.read.IceCreamWithDetails
import com.example.atividadesomativa2.model.entity.crossreference.IceCreamScoopCrossRef
import com.example.atividadesomativa2.model.entity.crossreference.IceCreamToppingCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface IceCreamDao {
    @Transaction
    @Query("SELECT * FROM ice_creams")
    fun getAll(): Flow<List<IceCreamWithDetails>>

    @Transaction
    @Query("SELECT * FROM ice_creams WHERE id = :id")
    suspend fun getById(id: Int): IceCreamWithDetails?

    @Transaction
    @Query("SELECT * FROM ice_creams WHERE orderId = :orderId")
    fun getByOrderId(orderId: Int): Flow<List<IceCreamWithDetails>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertScoopCrossRefs(crossRefs: List<IceCreamScoopCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToppingCrossRefs(crossRefs: List<IceCreamToppingCrossRef>)

    @Insert
    suspend fun add(iceCream: IceCream): Long

    @Transaction
    suspend fun addFullIceCream(orderId: Int, input: IceCreamInput, orderDao: OrderDao): Int {
        val iceCreamId = add(
            IceCream(
                orderId = orderId,
                container = input.container,
                quantity = input.quantity
            )
        ).toInt()

        val scoopRefs = input.scoopIdsAndQuantities.map { entry ->
            IceCreamScoopCrossRef(
                iceCreamId = iceCreamId,
                scoopId = entry.key,
                scoopQuantity = entry.value
            )
        }

        insertScoopCrossRefs(scoopRefs)

        val toppingRefs = input.toppingIdsAndQuantities.map { entry ->
            IceCreamToppingCrossRef(
                iceCreamId = iceCreamId,
                toppingId = entry.key,
                toppingQuantity = entry.value
            )
        }

        insertToppingCrossRefs(toppingRefs)

        orderDao.updateLastUpdatedAt(orderId)

        return iceCreamId
    }

    @Update
    suspend fun update(iceCream: IceCream)

    @Delete
    suspend fun delete(iceCream: IceCream)

    @Query("DELETE FROM ice_cream_scoop_cross_ref WHERE iceCreamId = :iceCreamId")
    suspend fun deleteScoopCrossRefs(iceCreamId: Int)

    @Query("DELETE FROM ice_cream_topping_cross_ref WHERE iceCreamId = :iceCreamId")
    suspend fun deleteToppingCrossRefs(iceCreamId: Int)

    @Query("DELETE FROM ice_creams WHERE orderId = :orderId")
    suspend fun deleteIceCreamsByOrder(orderId: Int)

    @Transaction
    suspend fun updateFullIceCream(orderId: Int, input: IceCreamUpdateInput, orderDao: OrderDao) {
        update(
            IceCream(
                id = input.id,
                orderId = orderId,
                container = input.container,
                quantity = input.quantity
            )
        )

        deleteScoopCrossRefs(input.id)
        deleteToppingCrossRefs(input.id)

        val newScoopRefs = input.scoopIdsAndQuantities.map { entry ->
            IceCreamScoopCrossRef(
                iceCreamId = input.id,
                scoopId = entry.key,
                scoopQuantity = entry.value
            )
        }
        insertScoopCrossRefs(newScoopRefs)

        val newToppingRefs = input.toppingIdsAndQuantities.map { entry ->
            IceCreamToppingCrossRef(
                iceCreamId = input.id,
                toppingId = entry.key,
                toppingQuantity = entry.value
            )
        }
        insertToppingCrossRefs(newToppingRefs)

        orderDao.updateLastUpdatedAt(orderId)
    }
}