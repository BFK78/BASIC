package com.example.basic.basic_feature.presentation.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import com.example.basic.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.basic.basic_feature.presentation.screens.account_screen.viewmodel.AccountScreenViewModel
import com.example.basic.ui.theme.prata
import com.example.basic.ui.theme.zillaSlab
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

val fieldNameList = listOf(
    "Email",
    "Mobile",
    "Username",
    "Password"
)

val fieldValueList = listOf(
    "basimbfk781@gmail.com",
    "8086396255",
    "Mohammed Basim",
    "***********"
)

@Composable
fun AccountScreen(
    viewModel: AccountScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val auth = Firebase.auth

    var address by remember {
        mutableStateOf("Karuvarpetta House, Pullpetta Post, \nManjeri, Kerala, Inddia, Pin: 676123")
    }

    var addressReadOnly by remember {
        mutableStateOf(true)
    }

    val image = remember {
        mutableStateOf(auth.currentUser?.photoUrl)
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        image.value = it
    }

    val imagePainter = rememberImagePainter(data = image.value) {
        crossfade(500)
    }

    val scrollState = rememberScrollState()

    val shadowValue = animateDpAsState(targetValue = if (scrollState.value > 0) 2.dp else 0.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(
                    color = MaterialTheme.colors.surface
                )
                .shadow(
                    elevation = shadowValue.value
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button",
                    tint = MaterialTheme.colors.onSurface
                )
            }

            IconButton(
                onClick = {
                    image.value?.let {
                        viewModel.updateProfilePic(image = it)
                    }
                    navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {

                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Done",
                    tint = MaterialTheme.colors.onSurface
                )

            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 64.dp)
                .verticalScroll(state = scrollState)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .width(130.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .clickable {
                            imageLauncher.launch("image/*")
                        }
                ) {
                    Image(
                        painter = if (image.value == null) painterResource(id = R.drawable.onboarding3) else imagePainter,
                        contentDescription = "User profile picture",
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Hey,",
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = auth.currentUser?.displayName ?: "",
                        style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.secondary
                        )
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                color = MaterialTheme.colors.secondary
            )

            repeat(times = 4) {
                SingleField(
                    fieldName = fieldNameList[it],
                    fieldVal = fieldValueList[it]
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Addresses",
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.h5.copy(
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Home Address",
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.h5.copy(
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextField(
                    value = address,
                    onValueChange = {
                        address = it
                    },
                    readOnly = addressReadOnly,
                    textStyle = MaterialTheme.typography.h5.copy(
                        color = if (addressReadOnly) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                        fontFamily = prata,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .width(300.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        focusedIndicatorColor = MaterialTheme.colors.secondary,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.secondary
                    )
                )
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {
                        addressReadOnly = !addressReadOnly
                        Log.i("icon", "edit")
                    }) {
                        Icon(
                            painter = painterResource(id = if (addressReadOnly) R.drawable.ic_baseline_edit_24 else R.drawable.ic_baseline_done_24),
                            contentDescription = "Edit Button",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SingleField(
    fieldName: String,
    fieldVal: String
) {

    val auth = Firebase.auth

    var readOnly by remember {
        mutableStateOf(true)
    }

//    val alphaEdit = animateFloatAsState(targetValue = if (readOnly) 1f else 0f,
//    animationSpec = tween(500)
//    )
//
//    val alphaDone = animateFloatAsState(targetValue = if (readOnly) 0f else 1f,
//    animationSpec = tween(500))

    var fieldValue by remember {
        mutableStateOf(fieldVal)
    }

    when(fieldName) {
        "Email" -> {
            fieldValue = auth.currentUser?.email ?: ""
        }
        "Username" -> {
            fieldValue = auth.currentUser?.displayName ?: ""
        }
        "Mobile" -> {
            fieldValue = ""
        }
    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = fieldName,
            color = MaterialTheme.colors.onSurface,
            fontFamily = zillaSlab,
            fontSize = 20.sp
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextField(
                value = fieldValue,
                onValueChange = {
                    fieldValue = it
                },
                readOnly = readOnly,
                singleLine = true,
                maxLines = 1,
                textStyle = MaterialTheme.typography.h5.copy(
                    color = if(readOnly) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                    fontFamily = prata,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .width(200.dp)
                    .height(TextFieldDefaults.MinHeight),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.secondary,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = MaterialTheme.colors.onBackground,
                    cursorColor = MaterialTheme.colors.secondary
                )
            )
            Box(
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { readOnly = !readOnly
                    Log.i("icon", "edit")
                }) {
                    Icon(
                        painter = painterResource( id = if (readOnly) R.drawable.ic_baseline_edit_24 else R.drawable.ic_baseline_done_24 ),
                        contentDescription = "Edit Button",
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}
