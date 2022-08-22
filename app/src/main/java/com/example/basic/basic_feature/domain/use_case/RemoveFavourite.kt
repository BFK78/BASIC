package com.example.basic.basic_feature.domain.use_case

import android.util.Log
import com.example.basic.basic_feature.data.repostiory.FavouriteRepository
import com.example.basic.basic_feature.domain.model.ProductModel

class RemoveFavourite(
    private val favouriteRepository: FavouriteRepository
) {
    suspend operator fun invoke(favourite: String) {
        Log.i("favourite", "yess it is getting called")
        favouriteRepository.deleteFavourite(favourite = favourite)
    }
}