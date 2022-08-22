package com.example.basic.basic_feature.domain.model

import com.example.basic.R

class ChipGroupModel(
    val image: Int,
    val title: String
) {
    companion object {
        fun get(): List<ChipGroupModel> {
            return listOf<ChipGroupModel>(
                ChipGroupModel(R.drawable.newarrivals,
                    title = "New Arrivals"),
                ChipGroupModel(R.drawable.tshirt,
                    title = "T Shirt"),
                ChipGroupModel(R.drawable.jogger,
                    title = "Joggers"),
                ChipGroupModel(R.drawable.shirt,
                    title = "Shirts")
            )
        }
        fun getChip(name: String): ChipGroupModel {
            val chipList = get()
            chipList.forEach {
                if (it.title == name) {
                    return it
                }
            }
            return ChipGroupModel(R.drawable.newarrivals, title = "New Arrivals")
        }
    }
}