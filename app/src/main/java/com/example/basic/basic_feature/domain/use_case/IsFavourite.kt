package com.example.basic.basic_feature.domain.use_case

import com.example.basic.basic_feature.data.repostiory.FavouriteRepository

class IsFavourite(
    private val favouriteRepository: FavouriteRepository
) {

    suspend operator fun invoke(productName: String): Boolean {
        return favouriteRepository.isFavourite(productName = productName)
    }
}