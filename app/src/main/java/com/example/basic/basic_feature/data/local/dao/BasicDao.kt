package com.example.basic.basic_feature.data.local.dao

import androidx.room.*
import com.example.basic.basic_feature.data.local.enitity.CollectionEntity
import com.example.basic.basic_feature.data.local.enitity.FBEntity
import com.example.basic.basic_feature.data.local.enitity.FavouriteEntity
import com.example.basic.basic_feature.data.local.enitity.HZEntity

@Dao
interface BasicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHzList(hzList: List<HZEntity>)

    @Query("SELECT * FROM horizontal_slider")
    suspend fun selectAllHz(): List<HZEntity>

    @Query("DELETE FROM horizontal_slider WHERE productName IN (:productList)")
    suspend fun deleteHzList(productList: List<String>)

    @Query("DELETE FROM horizontal_slider")
    suspend fun deleteAllHzList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFbList(hzList: List<FBEntity>)

    @Query("SELECT * FROM fashion_box")
    suspend fun selectAllFb(): List<FBEntity>

    @Query("DELETE FROM fashion_box WHERE productName IN (:productList)")
    suspend fun deleteFbList(productList: List<String>)

    @Query("DELETE FROM fashion_box")
    suspend fun deleteAllFbList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectionList(collectionList: List<CollectionEntity>)

    @Query("SELECT * FROM COLLECTION_TABLE WHERE collection = :collection")
    suspend fun selectAllCollection(collection: String): List<CollectionEntity>

    @Query("DELETE FROM COLLECTION_TABLE WHERE collection = :collection AND productName IN (:collectionList)")
    suspend fun deleteCollectionList(collectionList: List<String>, collection: String)

    @Query("DELETE FROM COLLECTION_TABLE WHERE collection = :collection")
    suspend fun deleteAllCollectionList(collection: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: FavouriteEntity)

    @Query("SELECT * FROM favourites_table")
    suspend fun selectAllFavourite(): List<FavouriteEntity>

    @Query("DELETE FROM FAVOURITES_TABLE WHERE productName= :productName")
    suspend fun removeFavourite(productName: String)

    @Query("SELECT * FROM favourites_table WHERE productName = :productName")
    suspend fun isFavourite(productName: String): FavouriteEntity
}