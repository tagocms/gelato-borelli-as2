package com.example.atividadesomativa2.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.atividadesomativa2.model.database.AppDatabase
import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.model.entity.Topping
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamContainer
import com.example.atividadesomativa2.model.entity.read.IceCreamWithDetails
import com.example.atividadesomativa2.model.repository.IceCreamRepository
import com.example.atividadesomativa2.model.repository.ScoopRepository
import com.example.atividadesomativa2.model.repository.ToppingRepository
import kotlinx.coroutines.launch

class IceCreamViewModel(application: Application, private val orderId: Int): AndroidViewModel(application) {
    private val repository: IceCreamRepository
    var scoops = mutableStateListOf<Scoop>()
        private set
    var toppings = mutableStateListOf<Topping>()
        private set

    init {
        val iceCreamDao = AppDatabase.getDatabase(application).iceCreamDao()
        val orderDao = AppDatabase.getDatabase(application).orderDao()
        repository = IceCreamRepository(iceCreamDao, orderDao)
        val scoopsDao = AppDatabase.getDatabase(application).scoopDao()
        val toppingsDao = AppDatabase.getDatabase(application).toppingDao()
        val scoopRepository = ScoopRepository(scoopsDao)
        val toppingRepository = ToppingRepository(toppingsDao)
        loadInitialData(scoopRepository, toppingRepository)
    }

    private fun loadInitialData(scoopRepository: ScoopRepository, toppingRepository: ToppingRepository) {
        viewModelScope.launch {
            scoopRepository.getAll().collect { list ->
                scoops.clear()
                scoops.addAll(list)
            }
            toppingRepository.getAll().collect { list ->
                toppings.clear()
                toppings.addAll(list)
            }
        }
    }

    fun isIceCreamValid(toppings: List<Topping>, scoops: List<Scoop>, container: IceCreamContainer, quantity: Int): Boolean {
        return repository.isIceCreamValid(toppings, scoops, container, quantity)
    }

    fun isToppingsValid(toppings: List<Topping>, container: IceCreamContainer): Boolean {
        return repository.isToppingsValid(toppings, container)
    }

    fun isScoopsValid(scoops: List<Scoop>, container: IceCreamContainer): Boolean {
        return repository.isScoopsValid(scoops, container)
    }

    fun isQuantityValid(quantity: Int): Boolean {
        return repository.isQuantityValid(quantity)
    }

    fun add(toppings: List<Topping>, scoops: List<Scoop>, container: IceCreamContainer, quantity: Int) {
        viewModelScope.launch {
            try {
                repository.add(orderId, scoops, toppings, container, quantity)
            } catch (e: Error) {
                // Handle the error
            }
        }
    }

    fun update(id:  Int, toppings: List<Topping>, scoops: List<Scoop>, container: IceCreamContainer, quantity: Int) {
        viewModelScope.launch {
            try {
                repository.update(orderId, id, scoops, toppings, container, quantity)
            } catch (e: Error) {
                // Handle the error
            }
        }
    }

    fun delete(iceCreamWithDetails: IceCreamWithDetails) {
        viewModelScope.launch {
            repository.delete(iceCreamWithDetails)
        }
    }
}