package com.example.basic.basic_feature.domain.navigation

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.basic.basic_feature.domain.model.ProductModel
import com.example.basic.basic_feature.presentation.viewmodels.BasicViewModel
import com.example.basic.basic_feature.presentation.screens.*
import com.example.basic.basic_feature.presentation.screens.stuf.OnBoardingScreen
import com.example.basic.basic_feature.presentation.screens.SettingScreen
import com.example.basic.basic_feature.presentation.screens.cart_screen.DetailedShoppingCart
import com.example.basic.basic_feature.presentation.screens.order_screen.OrderScreen
import com.example.basic.basic_feature.presentation.screens.stuf.SplashScreen
import com.example.basic.core.utils.CustomNavType
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
fun NavGraphBuilder.mainNavigation(
    navController: NavController,
    viewModel: BasicViewModel
) {
    navigation(
        startDestination = "Home_Screen",
        route = "Home_Route"
    ){

        composable(
            route = "ShoppingCart_Screen"
        ) {
            DetailedShoppingCart(
                navController = navController
            )
        }

        composable(route = "Home_Screen",
        exitTransition = {
            when(targetState.destination.route) {
                "Item_Screen/{model}" -> {
                    slideOutHorizontally(
                        targetOffsetX = { -500 },
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    ) + fadeOut(
                        tween(500)
                    )
                }
                "login_screen" -> {
                    fadeOut(tween(500))
                }
                else -> {
                    null
                }
            }
        },
        popEnterTransition = {
            when(initialState.destination.route) {
                "Item_Screen/{model}" -> {
                    slideInHorizontally(
                        initialOffsetX = { -500 },
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    ) + fadeIn(tween(500))
                }
                "Product_Screen/{collection}" -> {
                    fadeIn(
                        tween(500)
                    )
                }
                else -> {
                    null
                }
            }
        }) {
            DetailedHomeScreen(navController = navController, viewModel = viewModel)
        }
        //should remove this and put this into a separate function
        composable(route = "Item_Screen/{model}",
        arguments = listOf(
            navArgument(name = "model") {
                type = CustomNavType()
            }
        ),
        enterTransition = {
            when(initialState.destination.route) {
                "Home_Screen" -> {
                    slideInHorizontally(
                        initialOffsetX = { 500 },
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    ) + fadeIn(tween(500))
                }
                "Product_Screen/{collection}" -> {
                    slideInHorizontally(
                        initialOffsetX = { 500 },
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    ) + fadeIn(tween(500))
                }
                else -> {
                    null
                }
            }
        },
        popExitTransition = {
            when(targetState.destination.route) {
                "Home_Screen" -> {
                    slideOutHorizontally(
                        targetOffsetX = { 500 },
                        animationSpec = tween(
                            durationMillis = 500,
                        )
                    ) + fadeOut(tween(500))
                }
                "Product_Screen/{collection}" -> {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(
                            durationMillis = 500,
                        )
                    ) + fadeOut(tween(500),
                    .5f)
                }
                else -> {
                    null
                }
            }
        }) {
            val model = it.arguments?.getParcelable<ProductModel>("model")
            Log.i("navigation", model.toString())
            model?.let { hey ->
                DetailScreen(
                    model = hey,
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }

        composable("Product_Screen/{collection}",
        arguments = listOf(
            navArgument(name = "collection") {
                type = NavType.StringType
            }
        ),
        enterTransition = {
            when(initialState.destination.route) {
                "Home_Screen" -> {
                    expandVertically(
                        animationSpec = tween(500)
                    ) + fadeIn(
                        tween(500)
                    )
                }
                else -> {
                    null
                }
            }
        },
        popExitTransition = {
            when(targetState.destination.route) {
                "Home_Screen" -> {
                    shrinkVertically(
                        tween(500)
                    ) + fadeOut(
                        tween(500)
                    )
                }
                else -> {
                    null
                }
            }
        },
        exitTransition = {
            when(targetState.destination.route) {
                "Item_Screen/{model}" -> {
                    slideOutHorizontally(
                        targetOffsetX = { -500 },
                        animationSpec = tween(500)
                    ) + fadeOut(
                        animationSpec = tween(500)
                    )
                }
                else -> {
                    null
                }
            }
        },
        popEnterTransition = {
            when(initialState.destination.route) {
                "Item_Screen/{model}" -> {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(500)
                    ) + fadeIn(
                        tween(500)
                    )
                }
                else -> {
                    null
                }
            }
        }) {
            DetailedProductScreen(
                viewModel = viewModel,
                collection = it.arguments!!.getString("collection", ""),
                navController = navController)
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
fun NavGraphBuilder.starterNavigation(
    navController: NavController) {
    navigation(
        startDestination = "splash_screen",
        route = "Splash_Route"
    ){
        composable(route = "splash_screen") {
            SplashScreen(
                navController = navController
            )
        }
        composable(route = "on_boarding_screen"){
            OnBoardingScreen(
                navController = navController
            )
        }
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.authenticationNavigation(
    navController: NavController,
    viewModel: BasicViewModel
) {
    navigation(
        startDestination = "login_screen",
        route = "Authentication_Route"
    ) {
        composable(route = "login_screen",
        enterTransition = {
            when(initialState.destination.route) {
                "Home_Screen" -> {
                    expandHorizontally(
                        animationSpec = tween(
                            durationMillis = 500
                        ),
                        expandFrom = Alignment.CenterHorizontally
                    ) + fadeIn(
                        tween(
                            durationMillis = 500
                        )
                    )
                }
                else -> {
                    null
                }
            }
        },
            exitTransition =  {
                when(targetState.destination.route) {
                    "Home_Screen" -> {
                        scaleOut(
                            animationSpec = tween(500)
                        )
                    }
                    else -> {
                        null
                    }
                }
            },
            popExitTransition = {
                when(targetState.destination.route) {
                    "Home_Screen" -> {
                       scaleOut(
                           tween(500)
                       )
                    }
                    else -> {
                        null
                    }
                }
            }
        ) {
            SignInScreen(navController = navController,viewModel)
        }
        composable(route = "registration_screen") {
            RegistrationScreen(navController = navController,viewModel)
        }
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.navigationDrawerScreens(
    navController: NavController,
    viewModel: BasicViewModel
) {
    navigation(
        startDestination = "Account_Screen",
        route = "Drawer_Route"
    ){
        composable(
            route = "Account_Screen"
        ) {
          AccountScreen(
              navController = navController
          )
        }
        composable(
            route = "Settings_Screen"
        ) {
            SettingScreen(navController = navController)
        }


        composable(
            route = "Favourites_Screen"
        ) {
            FavouriteScreen(viewModel = viewModel, navController = navController )
        }

        composable(route = "Order_Screen") {
            OrderScreen(navController = navController)
        }

        composable(route = "Customer_Screen") {
            CustomerSupportScreen(
                navController = navController
            )
        }

        composable(route = "About_Screen") {
            AboutScreen(navController = navController)
        }
    }
}