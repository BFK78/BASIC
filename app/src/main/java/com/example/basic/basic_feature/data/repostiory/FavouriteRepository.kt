package com.example.basic.basic_feature.data.repostiory

import com.example.basic.basic_feature.data.local.dao.BasicDao
import com.example.basic.basic_feature.data.local.enitity.FavouriteEntity
import com.example.basic.basic_feature.domain.model.ProductModel


class FavouriteRepository(
    private val basicDao: BasicDao
) {

    suspend fun getAllFavourites(): List<ProductModel> {
        return basicDao.selectAllFavourite().map {
            it.toProductModel()
        }
    }

    suspend fun deleteFavourite(favourite: String) {
        basicDao.removeFavourite(productName = favourite)
    }

    suspend fun insertFavourite(favourite: ProductModel) {
        val newFavourite = favourite.toFavourite()
        basicDao.insertFavourite(favourite = newFavourite)
    }

    suspend fun isFavourite(productName: String): Boolean {
        return basicDao.isFavourite(productName = productName) != null
    }
}