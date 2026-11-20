package com.example.atividadesomativa2.model.repository

import com.example.atividadesomativa2.model.dao.OrderDao
import com.example.atividadesomativa2.model.entity.IceCream
import com.example.atividadesomativa2.model.entity.Order
import com.example.atividadesomativa2.model.entity.auxiliary.OrderStatus
import com.example.atividadesomativa2.model.entity.read.IceCreamWithDetails
import com.example.atividadesomativa2.model.entity.read.OrderWithIceCream
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

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

    suspend fun update(id: Int, orderStatus: OrderStatus) {
        orderDao.updateOrderStatus(id, orderStatus)
    }

    suspend fun delete(order: OrderWithIceCream) {
        orderDao.delete(order.order)
    }

    suspend fun getById(id: Int): OrderWithIceCream? {
        return orderDao.getById(id)
    }

    fun isOrderValid(iceCreams: List<IceCreamWithDetails>, status: OrderStatus?): Boolean {
        return isIceCreamsValid(iceCreams) && isStatusValid(status)
    }

    fun isIceCreamsValid(iceCreams: List<IceCreamWithDetails>): Boolean {
        return iceCreams.isNotEmpty()
    }

    fun isStatusValid(status: OrderStatus?): Boolean {
        return status != null
    }
}