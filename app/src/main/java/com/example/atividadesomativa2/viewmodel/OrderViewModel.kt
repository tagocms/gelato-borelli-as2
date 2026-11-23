package com.example.atividadesomativa2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividadesomativa2.model.database.AppDatabase
import com.example.atividadesomativa2.model.entity.auxiliary.OrderStatus
import com.example.atividadesomativa2.model.entity.read.IceCreamWithDetails
import com.example.atividadesomativa2.model.entity.read.OrderWithIceCream
import com.example.atividadesomativa2.model.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: OrderRepository
    var orders = mutableListOf<OrderWithIceCream>()
        private set
    var iceCreams = mutableMapOf<Int, List<IceCreamWithDetails>>()
        private set

    init {
        val orderDao = AppDatabase.getDatabase(application).orderDao()
        repository = OrderRepository(orderDao)
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                orders.clear()
                orders.addAll(list)

                list.forEach { order ->
                    iceCreams[order.order.id] = order.iceCreams
                }
            }
        }
    }

    fun isOrderValid(iceCreams: List<IceCreamWithDetails>, status: OrderStatus?): Boolean {
        return repository.isOrderValid(iceCreams, status)
    }

    fun isIceCreamsValid(iceCreams: List<IceCreamWithDetails>): Boolean {
        return repository.isIceCreamsValid(iceCreams)
    }

    fun isStatusValid(status: OrderStatus?): Boolean {
        return repository.isStatusValid(status)
    }

    fun add() {
        viewModelScope.launch {
            repository.add()
        }
    }

    fun update(id: Int, orderStatus: OrderStatus) {
        viewModelScope.launch {
            repository.update(id, orderStatus)
        }
    }

    fun delete(order: OrderWithIceCream) {
        viewModelScope.launch {
            repository.delete(order)
        }
    }

    fun getById(id: Int): OrderWithIceCream? {
        return orders.find { it.order.id == id }
    }
}