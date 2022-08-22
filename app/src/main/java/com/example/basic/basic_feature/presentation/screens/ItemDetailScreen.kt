package com.example.basic.basic_feature.presentation.screens

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.example.basic.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.basic.basic_feature.domain.model.ProductModel
import com.example.basic.basic_feature.presentation.viewmodels.BasicViewModel
import com.example.basic.basic_feature.presentation.screens.cart_screen.viewmodel.CartViewModel
import com.example.basic.basic_feature.presentation.screens.stuf.Indicators
import com.example.basic.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun ImageContainer(
    model: ProductModel
) {
    val imageList = model.productImage
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (imageList != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            ) {
            HorizontalPager(
                count = imageList.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            ) { page ->
                val painter = rememberImagePainter(
                    data = imageList[page],
                    builder = {
                        crossfade(500)
                        error(R.drawable.onboarding1)
                    }
                )
                val painterState = painter.state
                    if (painterState is ImagePainter.State.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colors.secondary
                        )
                    }
                    Image(
                        painter = painter,
                        contentDescription = "Item image",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Indicators(
                    index = pagerState.currentPage,
                    count = model.productImage!!.size,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                )
            }
        } else {
            Log.i("pr", "something bad")
        }
        PriceContainer(
            itemName = model.productName.toString(),
            price = model.productPrice.toString(),
            modifier = Modifier,
            previousPrice = model.productPreviousPrice.toString(),
            brandName = model.productBrand.toString(),
            sizes = model.productSize!!
        )
    }
}

@Composable
fun ItemScreenTopSection(
    navController: NavController,
    translateY: Float,
    modifier: Modifier,
    viewModel: BasicViewModel,
    productModel: ProductModel
) {
    var favourite by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        favourite = viewModel.isFavourite(productName = productModel.productName!!)
    }
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 60.dp)
            .graphicsLayer {
                translationY = translateY
            }
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.surface, Color.Transparent
                    )
                )
            )
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Button",
                tint = MaterialTheme.colors.onSurface
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = {
                favourite = if (favourite) {
                    scope.launch {
                        viewModel.removeFavourite(favouriteEntity = productModel.productName!!)
                    }
                    false
                }else {
                    scope.launch {
                        viewModel.insertFavourite(favouriteEntity = productModel)
                    }
                    true
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourite Button",
                    tint = if (favourite) Color.Red else MaterialTheme.colors.onSurface
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

@Composable
fun PriceContainer(
    itemName: String,
    brandName: String,
    price: String,
    sizes: List<String>,
    previousPrice: String,
    modifier: Modifier) {

    Column(
        modifier = modifier
            .padding(top = 24.dp,start = 24.dp, end = 24.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = brandName,
                style = MaterialTheme.typography.h4,
            )
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Text(
                text = itemName,
                style = MaterialTheme.typography.h5,
                fontFamily = zillaSlab,
                modifier = Modifier.padding(bottom = 2.dp),
                fontSize = 20.sp
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row (
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
                    ){
                Text(
                    text = "$$previousPrice",
                    textDecoration = TextDecoration.LineThrough,
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = "$$price",
                    style = MaterialTheme.typography.h4,
                    fontFamily = prata
                )
            }
            AddItem(modifier = Modifier)
        }
        Divider(
            color = MaterialTheme.colors.primary,
            thickness = 2.dp,
            modifier = Modifier
                .padding(vertical = 24.dp))
        SizeContainer(sizeList = sizes)
    }
}

@Composable
fun AddItem(
    modifier: Modifier
) {
    val itemCount = remember {
        mutableStateOf(1)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { itemCount.value++ },
            modifier = Modifier
                .background(Color(0xFF121212))
                .height(32.dp)
                .width(32.dp)) {
            Icon(imageVector = Icons.Default.Add,
                contentDescription = "Add item",
                tint = Color.White)
        }
        Box(
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .background(MaterialTheme.colors.background)) {
            Text(
                text = itemCount.value.toString(),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(Alignment.Center),
                fontFamily = FontFamily.Default
            )
        }
        IconButton(onClick = {
             if (itemCount.value > 1){
                 itemCount.value--
             }
        },
            modifier = Modifier
                .background(Color(0xFF121212))
                .height(32.dp)
                .width(32.dp)) {
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = "Remove Item",
                tint = Color.White)
        }
    }
}

@Composable
fun SingleSize(
    size: String,
    modifier: Modifier,
    isSelected: Boolean,
    onItemSelectedCallback: (String)-> Unit
) {

    val textColor = animateColorAsState(
        targetValue =
        if (isSelected)
            secondary
        else
            primary_variant3,
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )

    val borderColor = animateColorAsState(
        targetValue =
        if (isSelected)
            secondary
        else
            Color.Transparent,
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )

    val borderWidth = animateDpAsState(
        targetValue =
        if (isSelected)
            1.dp
        else
            0.dp,
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = modifier
            .height(32.dp)
            .width(32.dp)
            .clip(CircleShape)
            .border(
                width = borderWidth.value,
                color = borderColor.value,
                shape = CircleShape
            )
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onItemSelectedCallback(size)
                }
            )
            .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center
            ) {
            Text(
                text = size,
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                color = textColor.value
            )
       }
    }

@Composable
fun SizeGroup(
    sizeList: List<String>,
    selected: String,
    onSelectedCallBack: (String)-> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(sizeList) {item ->
            SingleSize(
                size = item,
                modifier = Modifier.padding(start = 16.dp),
                isSelected = selected == item,
                onSelectedCallBack
            )
        }
    }
}

@Composable
fun SizeContainer(
    sizeList: List<String>
) {
    val selectedChip = remember {
        mutableStateOf("M")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Size"
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        SizeGroup(
            sizeList = sizeList ,
            selected = selectedChip.value,
            onSelectedCallBack = { it ->
                selectedChip.value = it
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BasicButtons(
    modifier: Modifier,
    model: ProductModel,
    viewModel: CartViewModel = hiltViewModel()
) {

    val isDialogOpen = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = isDialogOpen.value) {
        delay(3000)
        isDialogOpen.value = false
    }

    AnimatedVisibility(
        visible = isDialogOpen.value,
        enter = scaleIn(
            animationSpec = tween(500)
        ),
        exit = scaleOut(
            tween(
                durationMillis = 500
            )
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AddItemToCartDialog(isDialogOpen = isDialogOpen)
        }
    }


    Row (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults
                .outlinedButtonColors(backgroundColor = MaterialTheme.colors.primary),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colors.primary)
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
                    .background(MaterialTheme.colors.primary),
                text = "Buy now",
                color = MaterialTheme.colors.secondary,
                fontFamily = zillaSlab
            )
        }

        Button(
            onClick = {
                viewModel.insertCartItem(model.toCartEntity())
                isDialogOpen.value = true
            },
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.primary
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colors.primary)
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
                    .background(MaterialTheme.colors.primary),
                text = "Add to cart",
                color = MaterialTheme.colors.onSurface,
                fontFamily = zillaSlab
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun DetailScreen(
    model: ProductModel,
    navController: NavController,
    viewModel: BasicViewModel
) {
    val topHeight = 80.dp
    val topHeightPx = with(LocalDensity.current) {
        topHeight.roundToPx().toFloat()
    }
    val topOffsetPx = remember { mutableStateOf(0f) }
    val nestedScrollState = remember {
        object: NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = topOffsetPx.value + delta
                topOffsetPx.value = newOffset.coerceIn(
                    -topHeightPx,0f
                )
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(connection = nestedScrollState)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.surface)
        ) {
            ImageContainer(
                model = model
            )
            BasicButtons(
                modifier = Modifier.padding(
                    top = 24.dp
                ),
                model = model
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
        ItemScreenTopSection(
            navController = navController,
            translateY = topOffsetPx.value,
            modifier = Modifier
                .align(alignment = Alignment.TopStart),
        viewModel = viewModel,
        productModel = model
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddItemToCartDialog(
    isDialogOpen: MutableState<Boolean>
) {

    val infiniteTransition = rememberInfiniteTransition()

    val scale = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    Dialog(
        onDismissRequest = { isDialogOpen.value = false },
        properties = DialogProperties()
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = MaterialTheme.colors.surface)
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Item added to the cart",
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colors.onSecondary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24),
                        contentDescription = "Added to cart",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale.value
                                scaleY = scale.value
                            }
                    )
                }
            }
        }

    }
}

