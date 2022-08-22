package com.example.basic.basic_feature.presentation.screens.stuf

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.basic.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.basic.basic_feature.data.local.datastore.addShown
import com.example.basic.basic_feature.data.local.datastore.readShown
import com.example.basic.basic_feature.domain.model.OnBoardingModel
import com.example.basic.ui.theme.BASICTheme
import com.example.basic.ui.theme.prata
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@Composable
fun SplashScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val shown = readShown(context = context).collectAsState(initial = false)

    LaunchedEffect(key1 = true) {
        delay(1500)
        if (shown.value) {
            navController.popBackStack()
            navController.navigate("Home_Route")
        }else {
            navController.popBackStack()
            navController.navigate("on_boarding_screen")
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            color = MaterialTheme.colors.onSecondary
        ),
        contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = R.drawable.whitebasic),
            contentDescription = "basic logo",
            modifier = Modifier
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(navController: NavController) {
    val pagerState = rememberPagerState()
    val dataList = OnBoardingModel.get()
    val textAlpha = animateFloatAsState(targetValue = if (pagerState.currentPage == 2)1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutLinearInEasing
        )
    )
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        addShown(context = context)
    }

    Box(modifier = Modifier
        .fillMaxSize()) {
        HorizontalPager(count = 3, state = pagerState) { page ->
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.weight(3.5f)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = dataList[page].image),
                        contentDescription = "On Boarding Image",
                        contentScale = ContentScale.Crop
                    )
                    if (pagerState.currentPage == 2) {
                        SlidingBox(
                            modifier = Modifier
                                .align(
                                    Alignment.BottomEnd
                                )
                                .alpha(
                                    alpha = textAlpha.value
                                ),
                            navController = navController
                        )
                    }
                }
                QuoteContainer(quote = dataList[page].quote)
            }
        }
        Indicators(index = pagerState.currentPage)
    }
}

@ExperimentalMaterialApi
@Composable
fun ColumnScope.QuoteContainer(
    quote: String,
    background: Color = MaterialTheme.colors.onSecondary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(background)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = LinearOutSlowInEasing
                )
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "BE \n$quote",
            fontSize = MaterialTheme.typography.h4.fontSize,
            letterSpacing = 5.sp,
            fontFamily = FontFamily(Font(R.font.prata)),
            modifier = Modifier.padding(start = 24.dp),
            color = Color.White
        )
        Spacer(modifier = Modifier.width(50.dp))
        Image(
            modifier = Modifier.fillMaxHeight(),
            painter = painterResource(id = R.drawable.half_circle),
            contentDescription = "half circle design",
            contentScale = ContentScale.FillHeight
        )
    }
}


@ExperimentalMaterialApi
@Composable
fun SlidingBox(
    modifier: Modifier,
    navController: NavController
) {
    val localConfiguration = LocalConfiguration.current
    val screenWidth = localConfiguration.screenWidthDp.dp - 80.dp

    val sizePx = with(LocalDensity.current) { screenWidth.toPx() }
    val swipeState = rememberSwipeableState(initialValue = 1) {
        if (it == 0) {
            navController.popBackStack()
            navController.navigate("Home_Route")
        }
        true
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                MaterialTheme.colors.background.copy(
                    alpha = 0.15f
                )
            )
            .swipeable(
                state = swipeState,
                orientation = Orientation.Horizontal,
                anchors = mapOf(0f to 0, sizePx to 1)
            )
    ) {
        Box(
            modifier = Modifier
                .size(80.dp, 40.dp)
                .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp,
                        bottomStart = 15.dp
                    )
                )
                .background(
                    color = MaterialTheme.colors.onPrimary
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                    contentDescription = "Swipe direction icon",
                    tint = MaterialTheme.colors.background,
                    modifier = Modifier.size(
                        24.dp
                    )
                )
                Text(
                    text = "Swipe",
                    style = MaterialTheme.typography.h6.copy(
                        fontFamily = prata,
                        color = MaterialTheme.colors.background
                    )
                )
            }
        }
    }
}


