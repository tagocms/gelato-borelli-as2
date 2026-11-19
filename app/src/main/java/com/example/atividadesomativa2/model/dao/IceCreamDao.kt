package com.example.atividadesomativa2.model.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.atividadesomativa2.model.entity.IceCream
import kotlinx.coroutines.flow.Flow

@Dao
interface IceCreamDao {
    @Query("SELECT * FROM ice_creams")
    fun getAll(): Flow<List<IceCream>>

    @Insert
    suspend fun add(iceCream: IceCream)

    @Update
    suspend fun update(iceCream: IceCream)

    @Delete
    suspend fun delete(iceCream: IceCream)

    @Query("SELECT * FROM ice_creams WHERE id = :id")
    suspend fun getById(id: Int): IceCream?
}