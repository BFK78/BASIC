package com.example.basic.basic_feature.presentation.screens

import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import com.example.basic.R
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.basic.basic_feature.domain.model.ChipGroupModel
import com.example.basic.basic_feature.domain.model.NavigationDrawerModel
import com.example.basic.basic_feature.presentation.viewmodels.BasicViewModel
import com.example.basic.basic_feature.presentation.screens.stuf.Indicators
import com.example.basic.core.utils.Constants.FASHION_BOX
import com.example.basic.core.utils.Constants.HORIZONTAL_SLIDER
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.jar.Manifest


@Composable
fun UniChip(
    navController: NavController,
    image: Int,
    modifier: Modifier,
    isSelected: Boolean ,
    gradient: Brush = Brush.verticalGradient(listOf(Color.Transparent, Color(0xFF121212))),
    onSelectionChanged: (String) -> Unit = {},
    chipText: String
) {

    val borderColor = animateColorAsState(
        if (isSelected) MaterialTheme.colors.secondary else Color.Transparent,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )

        Box(modifier = modifier
            .size(width = 87.dp, height = 77.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .toggleable(
                value = isSelected,
                onValueChange = {
                    navController.navigate("Product_Screen/$chipText")
                    onSelectionChanged(chipText)
                }
            )
            .border(
                width = 1.dp,
                color = borderColor.value,
                shape = RoundedCornerShape(20.dp)
            )
        ) {
            Image(painter = painterResource(id = image),
                contentDescription = "chip image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)

            Box(modifier = Modifier
                .background(gradient)
                .fillMaxSize()) {
            }

            Text(text = chipText,
                letterSpacing = 2.sp,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.prata)),
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 6.dp)
            )
        }
    }


@Composable
fun ChipGroup(
    navController: NavController,
    onSelectionChanges: (String) -> Unit,
    selectedChip: ChipGroupModel? = ChipGroupModel.getChip("New Arrivals"),
    modifier: Modifier
) {

    val chipData = ChipGroupModel.get()
    Column(modifier = modifier) {
        LazyRow (contentPadding = PaddingValues(end = 16.dp)){
            items(chipData) {item ->
                UniChip(
                    navController = navController,
                    chipText = item.title,
                    onSelectionChanged = {
                        onSelectionChanges(it)
                    },
                    isSelected = selectedChip?.title == item.title,
                    modifier = Modifier.padding(start = 16.dp),
                    image = item.image
                )
            }
        }
    }
}


@Composable
fun HomeTopSection(
    navController: NavController,
    scroll: Boolean,
    onClicked: ()-> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = if (scroll) 2.dp else 0.dp)
            .background(MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onClicked() }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account Icon",
                tint = MaterialTheme.colors.onSurface)
        }
        Image(painter = painterResource(id = R.drawable.whitebasic),
            contentDescription = "basic black icon",
            modifier = Modifier.height(12.dp))
        IconButton(onClick = {
            navController.navigate("ShoppingCart_Screen")
        }) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Cart Icon",
                tint = MaterialTheme.colors.onSurface)
        }
    }
}

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun HomeMainImageContainer(
    modifier: Modifier,
    viewModel: BasicViewModel,
    navController: NavController
) {
    val state = viewModel.state.value
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = true) {
        Log.i("hz", "its inside launched effect")
        viewModel.getHzData(HORIZONTAL_SLIDER)
    }

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(count = state.productList.size, state = pagerState,
            modifier = Modifier
                .height(400.dp)) { page ->
            val painter = rememberImagePainter(
                data = state.productList[page].productImage?.get(0),
                builder =  {
                    crossfade(500)
                    error(R.drawable.onboarding1)
                }
            )
            val painterState = painter.state

            Box(modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(25.dp))
                .clickable {
                    val model = state.productList[page]
                    val json = Uri.encode(Gson().toJson(model))
                    navController.navigate("Item_Screen/$json")
                }) {

                Image(painter = painter,
                    contentDescription = "Home Screen Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop)
                if (painterState is ImagePainter.State.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.secondary
                    )
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colors.primary.copy(
                            alpha = .3f
                        )
                    ))
                state.productList[page].productOffer?.let {
                    Text(text = "$it%",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 16.dp, top = 0.dp),
                        color = Color.White,
                        fontSize = MaterialTheme.typography.h3.fontSize,
                        fontWeight = MaterialTheme.typography.h3.fontWeight,
                        fontFamily = FontFamily(Font(R.font.prata)))
                }
                if (state.isLoading && state.productList.isEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.primary,
                thickness = 2.dp
            )
            Indicators(
                index = pagerState.currentPage,
                modifier = Modifier.padding(24.dp)
            )
            Divider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.primary,
                thickness = 2.dp
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun FashionBox(
    modifier: Modifier,
    viewModel: BasicViewModel,
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        viewModel.getFbData(FASHION_BOX)
    }

    val state = viewModel.fbState.collectAsState().value

        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(25.dp))
                .background(
                    Color(0x07FFFFFF)
//                Brush.verticalGradient(
//                    listOf(Color(0x10CECECE), Color(0x80121212))
//                )
                )
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Everyday Men's Fashion",
                fontFamily = FontFamily(Font(R.font.prata)),
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 16.dp, start = 4.dp)
            )
            if(state.productList.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary
                    )
                }
            } else {
                repeat(2) { a ->
                    var index = 0
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        index = if (a == 0) {
                            0
                        } else {
                            2
                        }

                        val painter1 = rememberImagePainter(
                            data = state.productList[index].productImage?.get(0),
                            builder = {
                                crossfade(500)
                                error(R.drawable.onboarding1)
                            }
                        )

                        val painterState1 = painter1.state
                        if (painterState1 is ImagePainter.State.Loading) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(150.dp)
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.secondary
                                )
                            }
                        } else {
                            Image(
                                painter = painter1,
                                contentDescription = "Fashion image container",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(150.dp)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        val model = state.productList[index]
                                        val json = Uri.encode(Gson().toJson(model))
                                        navController.navigate("Item_Screen/$json")
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }

                        val painter2 = rememberImagePainter(
                            data = state.productList[++index].productImage?.get(0),
                            builder = {
                                crossfade(500)
                                error(R.drawable.onboarding1)
                            }
                        )

                        val painterState2 = painter2.state
                        if (painterState2 is ImagePainter.State.Loading) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(150.dp)
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.secondary
                                )
                            }
                        } else {
                            Image(
                                painter = painter2,
                                contentDescription = "Fashion image container",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(150.dp)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable {
                                        val model = state.productList[++index]
                                        val json = Uri.encode(Gson().toJson(model))
                                        navController.navigate("Item_Screen/$json")
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
}

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: BasicViewModel,
    onClicked: () -> Unit

) {
    val selectedCar: MutableState<ChipGroupModel?> = remember {
        mutableStateOf(ChipGroupModel(R.drawable.newarrivals, title = "New Arrivals"))
    }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                listOf(
                    MaterialTheme.colors.onSecondary,
                    MaterialTheme.colors.surface
                )
            )
        )
    ) {
        HomeTopSection(scroll = scrollState.isScrollInProgress,
        navController = navController,
        onClicked = onClicked)
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
                ChipGroup(
                    navController = navController,
                    onSelectionChanges = {
                        selectedCar.value = ChipGroupModel.getChip(it)
                    },
                    selectedChip = selectedCar.value,
                    modifier = Modifier.padding(top = 24.dp)
                )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                color = MaterialTheme.colors.primary,
                thickness = 2.dp
            )

                HomeMainImageContainer(
                    modifier = Modifier,
                    viewModel = viewModel,
                    navController = navController
                )
                FashionBox(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 0.dp),
                    viewModel = viewModel,
                    navController = navController
                )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun DetailedHomeScreen(
    viewModel: BasicViewModel,
    navController: NavController
) {

    val context = LocalContext.current

    val activityPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {

    }

    LaunchedEffect(key1 = true) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {

            }
            else -> {
                activityPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            NavigationDrawer(
                drawerList = NavigationDrawerModel.get(),
                navController = navController
            ) {
                scope.launch { 
                    scaffoldState.drawerState.close()
                }
                navController.navigate("Authentication_Route")
            }
        }
    ) {
        HomeScreen(navController = navController, viewModel = viewModel) {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }
    }
}