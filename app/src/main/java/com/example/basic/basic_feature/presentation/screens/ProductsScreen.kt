package com.example.basic.basic_feature.presentation.screens

import android.net.Uri
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.basic.R
import com.example.basic.basic_feature.presentation.viewmodels.BasicViewModel
import com.example.basic.ui.theme.prata
import com.google.gson.Gson

@ExperimentalCoilApi
@Composable
fun SingleProduct(
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
                modifier = Modifier.fillMaxWidth()
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

@Composable
fun TopSectionProductScreen(
    collection: String,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .zIndex(2f)
            .fillMaxWidth()
            .height(60.dp)
            .shadow(elevation = 8.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.onSecondary,
                        MaterialTheme.colors.surface
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            Text(
                text = collection,
                style = MaterialTheme.typography.h5.copy(
                    fontSize = 20.sp
                )
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourite Button",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shopping Cart Button",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun SingleProductScreen(
    modifier: Modifier,
    collection: String,
    viewModel: BasicViewModel,
    navController: NavController,
    showKiller: (Boolean) -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.getTData(collection = collection)
    }

    val state = viewModel.tState.collectAsState().value

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

    LazyVerticalGrid(cells = GridCells.Fixed(2),
    modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(),
    state = lazyState) {
        items(state.productList.size) { it ->
            SingleProduct(
                imageUrl = state.productList[it].productImage?.get(0)?: "",
                price = state.productList[it].productPrice?: "Error 404",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                val model = state.productList[it]
                val json = Uri.encode(Gson().toJson(model))
                navController.navigate("Item_Screen/$json")
            }
        }
    }
}

@Composable
fun ProductScreenDesign(
    modifier: Modifier,
    show: Boolean
) {

    val opacity = animateFloatAsState(targetValue = if (show) 0f else -100f,
    animationSpec = tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    ))

    val opa = animateFloatAsState(targetValue = if (show) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .graphicsLayer {
                translationY = opacity.value
            }
            .alpha(opa.value)
    ) {
        Divider(
            color = MaterialTheme.colors.onSecondary,
            thickness = 5.dp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = MaterialTheme.colors.onSecondary.copy(alpha = 0.66F),
            thickness = 5.dp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Divider(
            color = MaterialTheme.colors.onSecondary.copy(alpha = 0.33F),
            thickness = 5.dp
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalCoilApi
@Composable
fun DetailedProductScreen(
    viewModel: BasicViewModel,
    collection: String,
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
            collection = collection,
            navController = navController
        )
        Box {
        val paddingVal = animateDpAsState(targetValue = if (show) 44.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ))
        SingleProductScreen(
            modifier = Modifier.padding(top = paddingVal.value),
            collection = collection,
            viewModel = viewModel,
            navController = navController
        ) { it ->
            show = it
        }
        ProductScreenDesign(
            modifier = Modifier.align(Alignment.Companion.TopStart)
                .padding(top = 8.dp),
            show = show
        )
    }
    }
}
