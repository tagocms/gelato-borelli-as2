package com.example.atividadesomativa2.model.repository

import com.example.atividadesomativa2.model.dao.OrderDao
import com.example.atividadesomativa2.model.entity.Order
import com.example.atividadesomativa2.model.entity.auxiliary.DraftIceCream
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamInput
import com.example.atividadesomativa2.model.entity.auxiliary.OrderStatus
import com.example.atividadesomativa2.model.entity.read.OrderWithIceCream
import kotlinx.coroutines.flow.Flow

class OrderRepository(
    private val orderDao: OrderDao
) {
    fun getAll(): Flow<List<OrderWithIceCream>> {
        return orderDao.getAll()
    }

    suspend fun add(orderStatus: OrderStatus = OrderStatus.PENDING) {
        orderDao.add(
            Order(status = orderStatus)
        )
    }

    suspend fun addAndReturnId(orderStatus: OrderStatus = OrderStatus.PENDING): Long {
        return orderDao.add(Order(status = orderStatus))
    }

    suspend fun saveOrderWithIceCreams(
        orderId: Int,
        status: OrderStatus,
        draftIceCreams: List<DraftIceCream>,
        iceCreamDao: com.example.atividadesomativa2.model.dao.IceCreamDao,
        orderDao: OrderDao
    ) {
        orderDao.updateOrderStatus(orderId, status)
        iceCreamDao.deleteIceCreamsByOrder(orderId)
        draftIceCreams.forEach { draft ->
            val scoopMap = getMapFromList(draft.scoops.map { it.id })
            val toppingMap = getMapFromList(draft.toppings.map { it.id })
            iceCreamDao.addFullIceCream(
                orderId = orderId,
                input = IceCreamInput(
                    container = draft.container,
                    scoopIdsAndQuantities = scoopMap,
                    toppingIdsAndQuantities = toppingMap,
                    quantity = draft.quantity
                ),
                orderDao = orderDao
            )
        }
    }

    private fun getMapFromList(list: List<Int>): Map<Int, Int> {
        val listIdsAndQuantities: MutableMap<Int, Int> = mutableMapOf()
        list.forEach {
            val currentQuantity = listIdsAndQuantities[it] ?: 0
            listIdsAndQuantities[it] = currentQuantity + 1
        }
        return listIdsAndQuantities
    }

    suspend fun update(id: Int, orderStatus: OrderStatus) {
        orderDao.updateOrderStatus(id, orderStatus)
    }

    suspend fun delete(order: OrderWithIceCream) {
        orderDao.delete(order.order)
    }

    suspend fun getById(id: Int): OrderWithIceCream? {
        return orderDao.getById(id)
    }

    fun isOrderValid(iceCreams: List<DraftIceCream>, status: OrderStatus?): Boolean {
        return isIceCreamsValid(iceCreams) && isStatusValid(status)
    }

    fun isIceCreamsValid(iceCreams: List<DraftIceCream>): Boolean {
        return iceCreams.isNotEmpty()
    }

    fun isStatusValid(status: OrderStatus?): Boolean {
        return status != null
    }
}