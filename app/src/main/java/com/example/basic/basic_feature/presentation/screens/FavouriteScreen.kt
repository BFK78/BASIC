package com.example.basic.basic_feature.presentation.screens

import android.net.Uri
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.basic.R
import com.example.basic.basic_feature.domain.model.ProductModel
import com.example.basic.basic_feature.presentation.viewmodels.BasicViewModel
import com.example.basic.core.utils.Constants.FAVOURITES
import com.example.basic.ui.theme.prata
import com.google.gson.Gson
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun FavouriteScreen(
    viewModel: BasicViewModel,
    navController: NavController
) {
        var show by remember {
            mutableStateOf(true)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface)
        ) {
            TopSectionProductScreen(
                collection = FAVOURITES,
                navController = navController
            )
            Box {
                val paddingVal = animateDpAsState(targetValue = if (show) 44.dp else 0.dp,
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                )
                FavouriteSingleProductScreen(
                    modifier = Modifier.padding(top = paddingVal.value),
                    viewModel = viewModel,
                    navController = navController
                ) { it ->
                    show = it
                }
                ProductScreenDesign(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 8.dp),
                    show = show
                )
            }
        }
    }



@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun FavouriteSingleProductScreen(
    modifier: Modifier,
    viewModel: BasicViewModel,
    navController: NavController,
    showKiller: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    var opacity by remember {
        mutableStateOf(1F)
    }
    val lazyState = rememberLazyListState()

    opacity = if (lazyState.firstVisibleItemIndex == 0) {
        1 - lazyState.firstVisibleItemScrollOffset.toFloat().coerceIn(0F,1F)
    }else {
        0F
    }

    if (opacity == 0F) {
        showKiller(false)
    }else {
        showKiller(true)
    }

    var state by remember {
        mutableStateOf(emptyList<ProductModel>())
    }

    LaunchedEffect(key1 = true ) {
        scope.launch {
            state = viewModel.getAllFavourite()
        }
    }

    LazyVerticalGrid(cells = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        state = lazyState) {
        items(state.size) { it ->
            FavouriteSingleProduct(
                imageUrl = state[it].productImage?.get(0)?: "",
                price = state[it].productPrice?: "Error 404",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                val model = state[it]
                val json = Uri.encode(Gson().toJson(model))
                navController.navigate("Item_Screen/$json")
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun FavouriteSingleProduct(
    imageUrl: String,
    price: String,
    modifier: Modifier = Modifier,
    onClick: ()-> Unit
) {
    val painter = rememberImagePainter(
        data = imageUrl
    ) {
        crossfade(500)
        this.error(R.drawable.onboarding1)
    }
    Box(
        modifier = modifier
            .height(280.dp)
            .width(180.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(15.dp))
            .clickable {
                onClick()
            }
    ) {
        Image(
            painter = painter,
            contentDescription = "Product Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .align(androidx.compose.ui.Alignment.Companion.BottomCenter)
                .background(MaterialTheme.colors.onSurface)
        ) {

            Text(
                text = "$$price",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 4.dp),
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = prata,
                    color = MaterialTheme.colors.primary,
                    fontSize = 20.sp
                )
            )
            Row(
                modifier = Modifier
                    .align(Alignment.Companion.CenterEnd)
            ) {

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourite",
                        tint = MaterialTheme.colors.primary
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24),
                        contentDescription = "Shopping Cart",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }

    }

}
