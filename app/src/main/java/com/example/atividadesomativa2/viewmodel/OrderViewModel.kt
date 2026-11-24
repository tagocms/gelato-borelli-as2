package com.example.atividadesomativa2.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividadesomativa2.model.database.AppDatabase
import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.model.entity.Topping
import com.example.atividadesomativa2.model.entity.auxiliary.DraftIceCream
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamContainer
import com.example.atividadesomativa2.model.entity.auxiliary.OrderStatus
import com.example.atividadesomativa2.model.entity.read.IceCreamWithDetails
import com.example.atividadesomativa2.model.entity.read.OrderWithIceCream
import com.example.atividadesomativa2.model.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: OrderRepository
    var orders = mutableStateListOf<OrderWithIceCream>()
        private set

    var draftIceCreams = mutableStateListOf<DraftIceCream>()
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
            }
        }
    }

    private var initializedOrderId: Int? = null

    fun initDraft(orderId: Int) {
        if (initializedOrderId == orderId) return
        initializedOrderId = orderId

        draftIceCreams.clear()
        val existing = getById(orderId)
        existing?.iceCreams?.forEach { details ->
            draftIceCreams.add(
                DraftIceCream(
                    id = details.iceCream.id,
                    container = details.iceCream.container,
                    quantity = details.iceCream.quantity,
                    scoops = details.scoops,
                    toppings = details.toppings
                )
            )
        }
    }

    fun addDraftIceCream(container: IceCreamContainer, quantity: Int, scoops: List<Scoop>, toppings: List<Topping>) {
        draftIceCreams.add(
            DraftIceCream(
                id = (draftIceCreams.minOfOrNull { it.id } ?: 0) - 1,
                container = container,
                quantity = quantity,
                scoops = scoops,
                toppings = toppings
            )
        )
    }

    fun updateDraftIceCream(id: Int, container: IceCreamContainer, quantity: Int, scoops: List<Scoop>, toppings: List<Topping>) {
        val index = draftIceCreams.indexOfFirst { it.id == id }
        if (index != -1) {
            draftIceCreams[index] = DraftIceCream(
                id = id,
                container = container,
                quantity = quantity,
                scoops = scoops,
                toppings = toppings
            )
        }
    }

    fun removeDraftIceCream(draft: DraftIceCream) {
        draftIceCreams.remove(draft)
    }

    fun saveOrderWithDrafts(orderId: Int, status: OrderStatus, onComplete: () -> Unit) {
        viewModelScope.launch {
            val appDb = AppDatabase.getDatabase(getApplication())
            repository.saveOrderWithIceCreams(
                orderId = orderId,
                status = status,
                draftIceCreams = draftIceCreams,
                iceCreamDao = appDb.iceCreamDao(),
                orderDao = appDb.orderDao()
            )
            resetDraft()
            onComplete()
        }
    }

    fun resetDraft() {
        initializedOrderId = null
    }

    fun isOrderValid(iceCreams: List<DraftIceCream>, status: OrderStatus?): Boolean {
        return repository.isOrderValid(iceCreams, status)
    }

    fun isIceCreamsValid(iceCreams: List<DraftIceCream>): Boolean {
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

    fun createOrderAndGetId(onCreated: (Int) -> Unit) {
        viewModelScope.launch {
            val id = repository.addAndReturnId().toInt()
            onCreated(id)
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