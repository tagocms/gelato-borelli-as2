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

class IceCreamViewModel(application: Application): AndroidViewModel(application) {
    private val repository: IceCreamRepository
    var allScoops = mutableStateListOf<Scoop>()
        private set
    var allToppings = mutableStateListOf<Topping>()
        private set
    var allIceCreams = mutableStateListOf<IceCreamWithDetails>()

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
                allScoops.clear()
                allScoops.addAll(list)
            }
        }

        viewModelScope.launch {
            toppingRepository.getAll().collect { list ->
                allToppings.clear()
                allToppings.addAll(list)
            }
        }

        viewModelScope.launch {
            repository.getAll().collect { list ->
                allIceCreams.clear()
                allIceCreams.addAll(list)
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

    fun add(orderId: Int, toppings: List<Topping>, scoops: List<Scoop>, container: IceCreamContainer, quantity: Int) {
        viewModelScope.launch {
            try {
                repository.add(orderId, scoops, toppings, container, quantity)
            } catch (e: Error) {
                // Handle the error
            }
        }
    }

    fun update(id:  Int, orderId: Int, toppings: List<Topping>, scoops: List<Scoop>, container: IceCreamContainer, quantity: Int) {
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

    fun getById(id: Int): IceCreamWithDetails? {
        return allIceCreams.find { it.iceCream.id == id }
    }
}