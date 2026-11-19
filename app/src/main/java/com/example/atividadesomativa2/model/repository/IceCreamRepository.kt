package com.example.atividadesomativa2.model.repository

import com.example.atividadesomativa2.model.dao.IceCreamDao
import com.example.atividadesomativa2.model.entity.IceCream
import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.model.entity.Topping
import com.example.atividadesomativa2.model.entity.auxiliary.IceCreamContainer
import kotlinx.coroutines.flow.Flow

class IceCreamRepository(
    private val iceCreamDao: IceCreamDao
) {
    fun getAll(): Flow<List<IceCream>> {
        return iceCreamDao.getAll()
    }

    suspend fun add(scoops: List<Scoop>, toppings: List<Topping>, container: IceCreamContainer) {
        iceCreamDao.add(
            IceCream(scoops = scoops, toppings = toppings, container = container)
        )
    }

    suspend fun update(id: Int, scoops: List<Scoop>, toppings: List<Topping>, container: IceCreamContainer) {
        iceCreamDao.update(
            IceCream(id = id, scoops = scoops, toppings = toppings, container = container)
        )
    }

    suspend fun delete(iceCream: IceCream) {
        iceCreamDao.delete(iceCream)
    }

    suspend fun getById(id: Int): IceCream? {
        return iceCreamDao.getById(id)
    }
}