package com.example.basic.basic_feature.presentation.screens.cart_screen

import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.basic.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.basic.basic_feature.data.local.enitity.CartEntity
import com.example.basic.basic_feature.presentation.screens.cart_screen.viewmodel.CartViewModel
import com.example.basic.core.utils.startPayment
import com.example.basic.ui.theme.prata
import com.google.gson.Gson
import com.razorpay.PaymentResultListener

@Composable
fun ShoppingCartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    navController: NavController
) {

    val localConfiguration = LocalConfiguration.current

    val scrollState = rememberScrollState()

    val cartItems = viewModel.cartState

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
            painterR = painterResource(R.drawable.ic_baseline_shopping_cart_24)
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
                    Text(text = "Cart is empty!! Keep shopping!")
                }

            } else {
                repeat(
                    cartItems.value.size
                ) {
                    SingleItem(
                        cartItem = cartItems.value[it]
                    ) {
                        val json = Uri.encode(Gson().toJson(cartItems.value[it]))
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

@Composable
fun ShoppingCartDesign(
    modifier: Modifier = Modifier,
    painterR: Painter
) {
    Row(
      modifier = modifier
          .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
                .weight(weight = 1f)
        )
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        25.dp
                    )
                )
                .background(
                    color = MaterialTheme.colors.background.copy(
                        alpha = 0.3f
                    )
                )
                .padding(
                    16.dp
                )
        ) {
            Icon(
                painter = painterR,
                contentDescription = "Shopping Cart Design Icon",
                modifier = Modifier.size(
                    96.dp
                ),
                tint = MaterialTheme.colors.onSurface
            )
        }
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .weight(weight = 1f)
        )
    }
}

@Composable
fun SingleItem(
    cartItem: CartEntity,
    viewModel: CartViewModel = hiltViewModel(),
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
        IconButton(
            onClick = {
                viewModel.deleteCartItem(cartEntity = cartItem)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Item"
            )
        }
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

@ExperimentalMaterialApi
@Composable
fun DetailedShoppingCart(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {

    val cartItems = viewModel.cartState

    var enable by remember {
        mutableStateOf(
            false
        )
    }

    LaunchedEffect(key1 = cartItems.value) {
        enable = cartItems.value.isNotEmpty()
    }

    val bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val bottomSheetValue = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    BottomSheetScaffold(
        sheetContent = {
           BottomSheetContent(
               totalPrice = viewModel.calculateTotalPrice(),
               discount = viewModel.calculateTotalDiscount(),
               delivery = "50"
           )
        },
        scaffoldState = bottomSheetValue,
        sheetPeekHeight = 32.dp,
        sheetShape = RoundedCornerShape(
            topStart = 32.dp,
            topEnd = 32.dp
        ),
        sheetBackgroundColor = MaterialTheme.colors.primaryVariant,
        sheetElevation = 20.dp,
        sheetGesturesEnabled = enable
    ) {
        ShoppingCartScreen(
            navController = navController
        )
    }
}

@Composable
fun BottomSheetContent(
    totalPrice: String,
    delivery: String,
    discount: String
) {

    val context = LocalContext.current

    Log.i("basim", context.toString())
    Log.i("basimll", context.getActivity().toString())

    val isSuccessDialogOpen = remember {
        mutableStateOf(false)
    }

    val isFailedDialogOpen = remember {
        mutableStateOf(false)
    }

    if (isSuccessDialogOpen.value) {

        PaymentDialog(
            isDialogOpen = isSuccessDialogOpen,
            message = "Payment Successful",
            icon = Icons.Default.Done,
            tint = androidx.compose.ui.graphics.Color.Green
        )
    }

    if (isFailedDialogOpen.value) {
        PaymentDialog(
            isDialogOpen = isFailedDialogOpen,
            message = "Payment Failed",
            icon = Icons.Default.Warning,
            tint = MaterialTheme.colors.error
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Divider(
                color = MaterialTheme.colors.surface,
                thickness = 6.dp,
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(5.dp)
                    )
                    .width(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        SingleItemBottomSheet(
            tagName ="Total Price",
            value = totalPrice
        )

        Spacer(modifier = Modifier.height(16.dp))
        SingleItemBottomSheet(
            tagName = "Delivery",
            value = delivery
        )

        Spacer(modifier = Modifier.height(16.dp))
        SingleItemBottomSheet(
            tagName = "Discount",
            value = discount
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.surface,
            thickness = 2.dp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "$${totalPrice.toInt() + delivery.toInt() - discount.toInt()}",
                style = MaterialTheme.typography.h6.copy(
                    fontFamily = prata
                )
            )
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.surface,
            thickness = 2.dp
        )
        Spacer(
            modifier = Modifier.height(
                16.dp
            )
        )
        Button(
            onClick = {
                object: PaymentResultListener {

                    override fun onPaymentSuccess(p0: String?) {
                        isSuccessDialogOpen.value = true
                        Log.i("basim", "ui")
                    }

                    override fun onPaymentError(p0: Int, p1: String?) {
                        isFailedDialogOpen.value = true
                        Log.i("basim", "uiw")
                    }
                }

                val activity = context.getActivity()
                startPayment(
                    amount = totalPrice.toInt(),
                    activity = activity!!
                )


            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary
            ),
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 12.dp
            )
        ) {
            Text(
                text = "CHECKOUT",
                style = MaterialTheme.typography.h5.copy(
                    color = MaterialTheme.colors.surface,
                    fontSize = 20.sp
                )
            )
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
    }
}

@Composable
fun SingleItemBottomSheet(
    tagName: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = tagName,
            style = MaterialTheme.typography.h6
        )
        Text(
            text = "$$value",
            style = MaterialTheme.typography.h6.copy(
                fontFamily = prata
            )
        )
    }
}

@Composable
fun PaymentDialog(
    isDialogOpen: MutableState<Boolean>,
    message: String,
    icon: ImageVector,
    tint: androidx.compose.ui.graphics.Color
) {

    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {

            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = message
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Icon(
                        imageVector = icon,
                        contentDescription = "Payment status icon",
                        tint = tint
                    )
                }
            }

        }
    }

}

val priceDetailList = listOf(
    "Total Price",
    "Delivery",
    "Discount"
)

val samplePrice = listOf(
    250,
    50,
    -50
)

fun Context.getActivity(): ComponentActivity? {
   return when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }
}