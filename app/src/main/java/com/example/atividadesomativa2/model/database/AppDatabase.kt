package com.example.atividadesomativa2.model.database

import android.content.Context
import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.atividadesomativa2.model.dao.IceCreamDao
import com.example.atividadesomativa2.model.dao.OrderDao
import com.example.atividadesomativa2.model.dao.ScoopDao
import com.example.atividadesomativa2.model.dao.ToppingDao
import com.example.atividadesomativa2.model.entity.IceCream
import com.example.atividadesomativa2.model.entity.Order
import com.example.atividadesomativa2.model.entity.Scoop
import com.example.atividadesomativa2.model.entity.Topping
import com.example.atividadesomativa2.model.entity.crossreference.IceCreamScoopCrossRef
import com.example.atividadesomativa2.model.entity.crossreference.IceCreamToppingCrossRef

@Database(
    entities = [
        IceCream::class,
        Order::class,
        Scoop::class,
        Topping::class,
        IceCreamScoopCrossRef::class,
        IceCreamToppingCrossRef::class
    ],
    version = 1
)
@ColumnTypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun iceCreamDao(): IceCreamDao
    abstract fun orderDao(): OrderDao
    abstract fun scoopDao(): ScoopDao
    abstract fun toppingDao(): ToppingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gelato_borelli.db"
                ).build()
                if (INSTANCE == null) {
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}