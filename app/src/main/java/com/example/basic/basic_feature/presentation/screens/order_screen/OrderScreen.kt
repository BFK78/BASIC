package com.example.basic.basic_feature.presentation.screens.order_screen

import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.basic.R
import com.example.basic.basic_feature.data.local.enitity.CartEntity
import com.example.basic.basic_feature.data.local.enitity.OrderEntity
import com.example.basic.basic_feature.domain.model.ProductModel
import com.example.basic.basic_feature.presentation.screens.cart_screen.ShoppingCartDesign
import com.example.basic.basic_feature.presentation.screens.cart_screen.SingleItem
import com.example.basic.basic_feature.presentation.screens.cart_screen.viewmodel.CartViewModel
import com.example.basic.basic_feature.presentation.viewmodels.MainViewModel
import com.example.basic.ui.theme.prata
import com.google.gson.Gson

@Composable
fun OrderScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {

    val localConfiguration = LocalConfiguration.current

    val scrollState = rememberScrollState()

    val cartItems = viewModel.orderItemState

    val dpi = (160.0 / localConfiguration.densityDpi.toFloat())

    val padValue = (48 - scrollState.value * dpi).toInt().dp.coerceAtLeast(0.dp)

    val shadowValue = animateDpAsState(targetValue = if (padValue == 0.dp) 2.dp else 0.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.surface
            )
    ) {
        ShoppingCartDesign(
            modifier = Modifier
                .padding(
                    top = padValue
                )
                .shadow(
                    elevation = shadowValue.value
                ),
            painterR = painterResource(R.drawable.ic_baseline_shopping_basket_24)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.surface
                )
                .verticalScroll(
                    state = scrollState,
                    enabled = true
                )
        ) {
            Spacer(
                modifier = Modifier.height(48.dp)
            )
            if (cartItems.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No items ordered yet!! Keep shopping!")
                }

            } else {
                repeat(
                    cartItems.value.size
                ) {
                    SingleOrderItem(
                        cartItem = cartItems.value[it].model
                    ) {
                        val json = Uri.encode(Gson().toJson(cartItems.value[it].model))
                        navController.navigate("Item_Screen/$json")
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(48.dp)
            )
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun SingleOrderItem(
    cartItem: ProductModel,
    onClick: () -> Unit
) {

    val painter = rememberImagePainter(
        data = cartItem.productImage?.get(0),
        builder = {
            crossfade(200)
            error(R.drawable.onboarding2)
        }
    )

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(88.dp)
                        .width(80.dp)
                        .clip(
                            RoundedCornerShape(20.dp)
                        )
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "Item Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onClick()
                            }
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = cartItem.productBrand ?: "",
                            style = MaterialTheme.typography.h5.copy(
                                fontSize = 20.sp
                            )
                        )
                        Text(
                            text = cartItem.productName ?: "",
                            style = MaterialTheme.typography.h6.copy(
                                color = MaterialTheme.colors.onBackground
                            )
                        )
                    }
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = cartItem.productPreviousPrice ?: "",
                            textDecoration = TextDecoration.LineThrough,
                            style = MaterialTheme.typography.h6.copy(
                                fontFamily = prata,
                                color = MaterialTheme.colors.onBackground
                            )
                        )
                        Text(
                            text = cartItem.productPrice ?: "",
                            style = MaterialTheme.typography.h5.copy(
                                fontSize = 20.sp,
                            )
                        )
                    }
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Quantity:",
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = "1",
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary
            )
        }
    }
}