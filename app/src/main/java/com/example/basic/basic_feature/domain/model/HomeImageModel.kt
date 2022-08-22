package com.example.basic.basic_feature.domain.model

import com.example.basic.R

class HomeImageModel(
    val image: Int,
    val Offer: String
) {
    companion object {
        fun get(): List<HomeImageModel> {
            return listOf(
                HomeImageModel(R.drawable.main_image,
                    Offer ="20% OFF"),
                HomeImageModel(R.drawable.onboarding3,
                    Offer = "50% OFF"),
                HomeImageModel(R.drawable.onboarding1,
                    Offer = "30% OFF")
            )
        }
        fun getImage(): List<Int> {
            return listOf(
                R.drawable.pexel1,
                R.drawable.pexel2,
                R.drawable.pexel3,
                R.drawable.pexel4
            )
        }
    }
}