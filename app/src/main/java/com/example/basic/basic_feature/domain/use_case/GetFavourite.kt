package com.example.basic.basic_feature.domain.use_case

import com.example.basic.basic_feature.data.local.enitity.FavouriteEntity
import com.example.basic.basic_feature.data.repostiory.FavouriteRepository
import com.example.basic.basic_feature.domain.model.ProductModel


class GetFavourite(
    private val favouriteRepository: FavouriteRepository) {

    suspend operator fun invoke(): List<ProductModel> {
        return favouriteRepository.getAllFavourites()
    }
}