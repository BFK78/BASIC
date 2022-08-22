package com.example.basic.basic_feature.data.local


import androidx.room.Database
import androidx.room.Index
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.basic.basic_feature.data.local.dao.BasicDao
import com.example.basic.basic_feature.data.local.dao.CartDao
import com.example.basic.basic_feature.data.local.dao.OrderDao
import com.example.basic.basic_feature.data.local.enitity.*

@Database(
    entities = [HZEntity::class, FBEntity::class, CollectionEntity::class, FavouriteEntity::class, CartEntity::class, OrderEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class BasicDatabase: RoomDatabase() {
    abstract val dao: BasicDao
    abstract val cartDao: CartDao
    abstract val orderDao: OrderDao
}