package com.example.basic.basic_feature.domain.use_case

import com.example.basic.basic_feature.data.local.enitity.FavouriteEntity
import com.example.basic.basic_feature.data.repostiory.FavouriteRepository
import com.example.basic.basic_feature.domain.model.ProductModel

class InsertFavourite(
    private val favouriteRepository: FavouriteRepository
) {

    suspend operator fun invoke(favourite: ProductModel) {
        favouriteRepository.insertFavourite(favourite = favourite)
    }
}