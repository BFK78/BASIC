
package com.example.basic.basic_feature.presentation.screens.stuf

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun UniIndicator(
    active: Boolean = false,
    activeBackground: Color = MaterialTheme.colors.secondary,
    background: Color = Color.Black
) {
    val indicatorWidth = animateDpAsState(
        targetValue = if (active) 25.dp else 10.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        )
    )
    Box(modifier = Modifier
        .width(indicatorWidth.value)
        .height(5.dp)
        .clip(CircleShape)
        .background(
            if (active) activeBackground else background
        )
    )
}

@Composable
fun BoxScope.Indicators(
    index: Int ,
    count: Int = 3
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .align(Alignment.Center)
            .padding(top = 480.dp)){
        repeat(count) {
            UniIndicator(active = it == index)
        }
    }
}

@Composable
fun Indicators(
    index: Int ,
    count: Int = 3,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier){
        repeat(count) {
            UniIndicator(active = it == index,
                activeBackground = Color.Yellow)
        }
    }
}
