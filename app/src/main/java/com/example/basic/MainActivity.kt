package com.example.basic

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.example.basic.basic_feature.domain.navigation.authenticationNavigation
import com.example.basic.basic_feature.domain.navigation.mainNavigation
import com.example.basic.basic_feature.domain.navigation.navigationDrawerScreens
import com.example.basic.basic_feature.domain.navigation.starterNavigation
import com.example.basic.basic_feature.presentation.viewmodels.BasicViewModel
import com.example.basic.basic_feature.presentation.viewmodels.MainViewModel
import com.example.basic.ui.theme.BASICTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {

    private val mainViewModel: Lazy<MainViewModel> = lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    @ExperimentalCoilApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {



            BASICTheme {
                val viewModel: BasicViewModel = hiltViewModel()
                SplashNavigation(viewModel = viewModel)
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        mainViewModel.value.insertItemsToOrder()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun SplashNavigation(
    viewModel: BasicViewModel
) {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = "Splash_Route",
    route = "Root_Route") {
        starterNavigation(navController = navController)
        mainNavigation(navController = navController, viewModel = viewModel)
        authenticationNavigation(navController = navController, viewModel = viewModel)
        navigationDrawerScreens(navController = navController, viewModel = viewModel)
    }
}