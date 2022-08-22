package com.example.basic.basic_feature.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.basic.R

class NavigationDrawerModel(
    val icon: Int,
    val title: String
) {
    companion object {
        fun get(): List<NavigationDrawerModel> {

            return listOf(
                NavigationDrawerModel(
                    icon = R.drawable.ic_baseline_account_circle_24,
                    title = "Account"
                ),
                NavigationDrawerModel(
                    icon = R.drawable.ic_baseline_settings_24,
                    title = "Settings"
                ),
                NavigationDrawerModel(
                    icon = R.drawable.ic_baseline_favorite_border_24,
                    title = "Favourites"
                ),
                NavigationDrawerModel(
                    icon = R.drawable.ic_baseline_phone_24,
                    title = "Contact Us"
                ),
                NavigationDrawerModel(
                    icon = R.drawable.ic_baseline_shopping_basket_24,
                    title = "My Orders"
                ),
                NavigationDrawerModel(
                    icon = R.drawable.ic_baseline_info_24,
                    title = "About Us"
                )
            )
        }
    }
}