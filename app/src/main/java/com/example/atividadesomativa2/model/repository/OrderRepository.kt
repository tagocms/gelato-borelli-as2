package com.example.atividadesomativa2.model.repository

import com.example.atividadesomativa2.model.dao.OrderDao
import com.example.atividadesomativa2.model.entity.IceCream
import com.example.atividadesomativa2.model.entity.Order
import kotlinx.coroutines.flow.Flow

class OrderRepository(
    private val orderDao: OrderDao
) {
    fun getAll(): Flow<List<Order>> {
        return orderDao.getAll()
    }

    suspend fun add(iceCreams: List<IceCream>) {
        orderDao.add(
            Order(iceCreams = iceCreams)
        )
    }

    suspend fun update(id: Int, iceCreams: List<IceCream>) {
        orderDao.update(
            Order(id = id, iceCreams = iceCreams)
        )
    }

    suspend fun delete(order: Order) {
        orderDao.delete(order)
    }

    suspend fun getById(id: Int): Order? {
        return orderDao.getById(id)
    }
}