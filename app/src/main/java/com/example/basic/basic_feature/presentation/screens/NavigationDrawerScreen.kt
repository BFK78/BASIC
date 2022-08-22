package com.example.basic.basic_feature.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.fadeIn
import com.example.basic.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.basic.basic_feature.domain.model.NavigationDrawerModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalCoilApi::class)
@Composable
fun NavigationDrawer(
    drawerList: List<NavigationDrawerModel>,
    navController: NavController,
    onClicked: () -> Unit
) {

    val context = LocalContext.current

    val auth = Firebase.auth

    val image = remember {
        mutableStateOf(auth.currentUser?.photoUrl)
    }

    val imagePainter = rememberImagePainter(data = image.value) {
        crossfade(500)
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.primaryVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(shape = CircleShape)
                .background(Color.Red)
                .clickable {
                    if (auth.currentUser != null) {
                        navController.navigate("Account_Screen")
                    } else {
                        Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                    }
                }
        ) {
            Image(
                painter = if (image.value == null) painterResource(id = R.drawable.onboarding3) else imagePainter,
                contentDescription = "User Profile Image",
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.secondary,
            thickness = 2.dp
        )
        Spacer(modifier = Modifier.height(24.dp))
        repeat(drawerList.size) {
            DrawerItem(
                icon = drawerList[it].icon,
                title = drawerList[it].title,
                navController = navController
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(48.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
            ){
            Text(
                text = if (auth.currentUser == null) "Not Logged In? " else "Welcome!,",
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )

            if (auth.currentUser != null) {
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = if (auth.currentUser == null) "Sign In" else auth.currentUser!!.email.toString(),
                modifier = Modifier.clickable {
                    if (auth.currentUser == null) {
                        onClicked()
                    }
                },
                style = MaterialTheme.typography.h5.copy(
                    color = MaterialTheme.colors.secondary,
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun DrawerItem(
    icon: Int,
    title: String,
    navController: NavController
) {
    val context = LocalContext.current
    val auth = Firebase.auth

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp)
            .clickable {
                when (title) {
                    "Settings" -> {
                        navController.navigate("Settings_Screen")
                    }
                    "Account" -> {
                        if (auth.currentUser != null) {
                            navController.navigate("Account_Screen")
                        } else {
                            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                        }
                    }
                    "Favourites" -> {
                        navController.navigate("Favourites_Screen")
                    }
                    "My Orders" -> {
                        navController.navigate("Order_Screen")
                    }
                    "Contact Us" -> {
                        navController.navigate("Customer_Screen")
                    }
                    "About Us" -> {
                        navController.navigate("About_Screen")
                    }
                }
            }
    ) {
        IconButton(onClick = {
            when (title) {
                "Settings" -> {
                    navController.navigate("Settings_Screen")
                }
                "Account" -> {
                    if (auth.currentUser != null) {
                        navController.navigate("Account_Screen")
                    } else {
                        Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                    }
                }
                "Favourites" -> {
                    navController.navigate("Favourites_Screen")
                }
                "My Orders" -> {
                    navController.navigate("Order_Screen")
                }
                "Contact Us" -> {
                    navController.navigate("Customer_Screen")
                }
                "About Us" -> {
                    navController.navigate("About_Screen")
                }
            }
        }) {
            Icon(painter = painterResource(id = icon), contentDescription = "Account icon",
            tint = MaterialTheme.colors.onSurface)
        }
        Text(
            text = title,
            color = MaterialTheme.colors.onSurface,
            fontSize = 20.sp
        )
    }
}
