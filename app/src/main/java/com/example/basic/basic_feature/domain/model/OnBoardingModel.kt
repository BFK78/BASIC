package com.example.basic.basic_feature.domain.model
import com.example.basic.R

class OnBoardingModel(
    val image: Int,
    val quote: String
) {

    companion object {
        fun get(): List<OnBoardingModel> {
            return listOf(
                OnBoardingModel(
                    R.drawable.onboarding1,
                    "FORMAL"
                ),
                OnBoardingModel(
                    R.drawable.onboarding2,
                    "UNIQUE"
                ),
                OnBoardingModel(
                    R.drawable.onboarding3,
                    "DIFFERENT"
                )
            )
        }
    }
}