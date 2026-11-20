package com.example.atividadesomativa2.model.repository

import com.example.atividadesomativa2.model.dao.IceCreamDao
import com.example.atividadesomativa2.model.dao.OrderDao
import com.example.atividadesomativa2.model.entity.IceCream
import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.model.entity.Topping
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamContainer
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamInput
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamUpdateInput
import com.example.atividadesomativa2.model.entity.read.IceCreamWithDetails
import kotlinx.coroutines.flow.Flow

class IceCreamRepository(
    private val iceCreamDao: IceCreamDao,
    private val orderDao: OrderDao
) {
    fun getAll(): Flow<List<IceCreamWithDetails>> {
        return iceCreamDao.getAll()
    }

    fun isIceCreamValid(toppings: List<Topping>, scoops: List<Scoop>, container: IceCreamContainer, quantity: Int): Boolean {
        return isToppingsValid(toppings, container) && isScoopsValid(scoops, container) && isQuantityValid(quantity)
    }

    fun isToppingsValid(toppings: List<Topping>, container: IceCreamContainer): Boolean {
        return toppings.size <= container.getToppings()
    }

    fun isScoopsValid(scoops: List<Scoop>, container: IceCreamContainer): Boolean {
        return scoops.isNotEmpty() && scoops.size <= container.getScoops()
    }

    fun isQuantityValid(quantity: Int): Boolean {
        return quantity > 0
    }

    suspend fun add(orderId: Int, scoops: List<Scoop>, toppings: List<Topping>, container: IceCreamContainer, quantity: Int) {
        if (!isIceCreamValid(toppings, scoops, container, quantity)) { throw Error("Invalid ice cream") }

        val scoopIdsAndQuantities = getMapFromList(scoops.map { it.id })
        val toppingsIdsAndQuantities = getMapFromList(toppings.map { it.id })

        iceCreamDao.addFullIceCream(
            orderId = orderId,
            input = IceCreamInput(
                container = container,
                scoopIdsAndQuantities = scoopIdsAndQuantities,
                toppingIdsAndQuantities = toppingsIdsAndQuantities,
                quantity = quantity
            ),
            orderDao = orderDao
        )
    }

    @Throws(Error::class)
    suspend fun update(orderId: Int, id: Int, scoops: List<Scoop>, toppings: List<Topping>, container: IceCreamContainer, quantity: Int) {
        if (!isIceCreamValid(toppings, scoops, container, quantity)) { throw Error("Invalid ice cream") }

        val scoopIdsAndQuantities = getMapFromList(scoops.map { it.id })
        val toppingsIdsAndQuantities = getMapFromList(toppings.map { it.id })

        iceCreamDao.updateFullIceCream(
            orderId = orderId,
            input = IceCreamUpdateInput(
                id = id,
                container = container,
                scoopIdsAndQuantities = scoopIdsAndQuantities,
                toppingIdsAndQuantities = toppingsIdsAndQuantities,
                quantity = quantity
            ),
            orderDao = orderDao
        )
    }

    suspend fun delete(iceCream: IceCreamWithDetails) {
        iceCreamDao.delete(iceCream.iceCream)
    }

    suspend fun getById(id: Int): IceCreamWithDetails? {
        return iceCreamDao.getById(id)
    }

    private fun getMapFromList(list: List<Int>): Map<Int, Int> {
        val listIdsAndQuantities: MutableMap<Int, Int> = mutableMapOf()
        list.forEach {
            val currentQuantity = listIdsAndQuantities[it] ?: 0
            listIdsAndQuantities[it] = currentQuantity + 1
        }
        return listIdsAndQuantities
    }
}