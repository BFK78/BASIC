package com.example.basic.basic_feature.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.basic.R
import com.example.basic.basic_feature.presentation.screens.cart_screen.ShoppingCartDesign
import com.example.basic.ui.theme.prata
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun SettingScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.surface
            )
            .fillMaxSize()
    ) {
        ShoppingCartDesign(
            modifier = Modifier.padding(vertical = 24.dp),
            painterR = painterResource(id = R.drawable.ic_baseline_settings_24)
        )
        SettingSingleItem() {
            navController.popBackStack()
        }
    }
}

@Composable
fun SettingSingleItem(
    onClick: () -> Unit
) {
    val auth = Firebase.auth
    val context = LocalContext.current
    Column {
        Divider(
            color = MaterialTheme.colors.primary
        )
        Button(
            onClick = {
                if (auth.currentUser != null) {
                    auth.signOut()
                    onClick()
                }else {
                    Toast.makeText(
                        context,
                        "You are not Logged In",
                        Toast.LENGTH_SHORT
                    ).show()
                    onClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Transparent
                ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            contentPadding = PaddingValues(0.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.h4.copy(
                    fontFamily = prata,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 12.dp
                    ),
                textAlign = TextAlign.Start
            )
        }
        Divider(
            color = MaterialTheme.colors.primary
        )
    }
}