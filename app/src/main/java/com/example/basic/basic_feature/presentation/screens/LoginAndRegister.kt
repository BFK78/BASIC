package com.example.basic.basic_feature.presentation.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.basic.R
import com.example.basic.basic_feature.presentation.viewmodels.BasicViewModel
import com.example.basic.core.utils.*

import com.example.basic.ui.theme.prata
import com.example.basic.ui.theme.primary_variant3
import com.example.basic.ui.theme.secondary
import com.example.basic.ui.theme.zillaSlab


val registerList = listOf("Email", "Username", "Mobile","Password")
val signList = listOf("Email", "Password")

@Composable
fun PlaceholderScreen(
    viewModel: BasicViewModel,
    action: String,
    fieldList: List<String>,
    accountCheck: String,
    accountAction: String,
    navController: NavController,
    onClicked: ()-> Unit
) {
    val context = LocalContext.current

    val state by viewModel.loadingState.collectAsState()

    var emailText by remember {
        mutableStateOf("")
    }

    var passwordText by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    val filledSet by remember {
        mutableStateOf(mutableSetOf<String>())
    }

    var isFilled by remember {
        mutableStateOf(false)
    }

    val actionColor = animateColorAsState(
        targetValue = if (!isFilled)
            primary_variant3
        else
            secondary,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    val moveX = animateFloatAsState(
        targetValue = if (isFilled) -12f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(key1 = state) {
        if (state == LoadingState.DONE) {
            navController.popBackStack()
            navController.navigate("Home_Route")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading == true) {
            LoadingDialog(context = context, username = emailText)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.verticalScroll(state = rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            if (action == "Sign in") {
                                viewModel.signIn(emailText, passwordText)
                            } else {
                                viewModel.createUser(emailText, passwordText, username = username)
                            }
                            if (state == LoadingState.DONE) {
                                //Not Really Configured for the Registration Screen so keep that in the mind!!
                                navController.popBackStack()
                                navController.navigate("Home_Route")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        contentPadding = PaddingValues(0.dp),
                        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = action,
                            style = MaterialTheme.typography.h4,
                            fontFamily = prata,
                            color = actionColor.value
                        )
                    }
                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_chevron_left_24),
                        contentDescription = "Guidance",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier
                            .graphicsLayer {
                                translationX = moveX.value
                            }
                            .alpha(if (isFilled) 1f else 0f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = actionColor.value
                )
                repeat(fieldList.size) { index ->
                    Spacer(
                        modifier = Modifier
                            .height(32.dp)
                    )
                    BasicTextField(
                        hint = fieldList[index],
                        modifier = Modifier.padding(horizontal = 24.dp),
                        { email ->
                            emailText = email
                        },
                        { password ->
                            passwordText = password
                        },
                        { user ->
                            username = user
                        },
                        { value ->
//                            filledSet.removeIf {
//                                filledSet.contains(value)
//                            }
                            filledSet.remove(value)
                            Log.i("setA", filledSet.toString())
                            isFilled = filledSet.containsAll(fieldList)
                        }
                    ) {
                        filledSet.add(it)
                        isFilled = filledSet.containsAll(fieldList)
                        Log.i("setB", filledSet.toString())
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.googleg_standard_color_18),
                            contentDescription = "google icon",
                            tint = Color.Unspecified
                        )
                    }

                    Spacer(modifier = Modifier.width(32.dp))

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.googleg_disabled_color_18),
                            contentDescription = "facebook logo"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = accountCheck,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Text(
                        text = accountAction,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.clickable {
                            onClicked()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SignInScreen(navController: NavController,
viewModel: BasicViewModel
) {
    PlaceholderScreen(
        viewModel = viewModel,
        action = "Sign in",
        fieldList = signList,
        accountCheck = "Don't have an account?",
        accountAction = "Register.",
        navController = navController)  {
        navController.navigate("registration_screen")
    }
}

@Composable
fun RegistrationScreen(navController: NavController,
viewModel: BasicViewModel
) {
    PlaceholderScreen(
        viewModel = viewModel,
        action = "Registration",
        fieldList = registerList,
        accountCheck = "Already have an account?",
        accountAction = "Sign in.",
        navController = navController){
        navController.popBackStack()
    }
}
@Composable
fun BasicTextField(
    hint: String,
    modifier: Modifier,
    onEmail: (String)-> Unit,
    onPassword: (String)-> Unit,
    onUsername: (String) -> Unit,
    onRemove:(String)-> Unit,
    onValueChanged: (String) -> Unit
) {

    val textFieldValue = remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf(false)
    }
    var message by remember {
        mutableStateOf("")
    }
    val alpha = animateFloatAsState(targetValue = if (error) 1f else 0f,
    animationSpec = tween(
        durationMillis = 300
    ))
    val color = animateColorAsState(targetValue = if (error) MaterialTheme.colors.error else MaterialTheme.colors.secondary,
    animationSpec = tween(
        durationMillis = 300
    ))

    Column(
        modifier = Modifier.animateContentSize(
            animationSpec = tween(
                durationMillis = 300
            )
        )
    ) {
        TextField(
            value = textFieldValue.value,
            onValueChange = {
                textFieldValue.value = it
                if (textFieldValue.value.length > 2) {
                    when (hint) {
                        "Email" -> {
                            if (textFieldValue.value.checkEmail()) {
                                onValueChanged(hint)
                                error = false
                            } else {
                                onRemove(hint)
                                message = "Enter a valid email."
                                error = true
                            }
                        }
                        "Password" -> {
                            if (textFieldValue.value.checkPassword()) {
                                onValueChanged(hint)
                                error = false
                            } else {
                                onRemove(hint)
                                message = "Password should have 8 character with at least 1 letter and 1 number."
                                error = true
                            }
                        }
                        "Mobile" -> {
                            if (textFieldValue.value.checkNumber()) {
                                onValueChanged(hint)
                                error = false
                            } else {
                                onRemove(hint)
                                message = "Enter a valid number"
                                error = true
                            }
                        }
                        "Username" -> {
                            if (textFieldValue.value.checkUsername()) {
                                onValueChanged(hint)
                                error = false
                            } else {
                                onRemove(hint)
                                message = "Username should have more than 5 characters"
                                error = true
                            }
                        }
                        else -> {
                            onValueChanged(hint)
                        }
                    }
                } else if (textFieldValue.value.length <= 2) {
                    onRemove(hint)
                }

                if (hint == "Email") {
                    onEmail(it)
                } else if (hint == "Password") {
                    onPassword(it)
                } else if (hint == "Username") {
                    onUsername(it)
                }
            },
            singleLine = true,
            maxLines = 1,
            modifier = modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colors.surface
                ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                backgroundColor = MaterialTheme.colors.surface,
                cursorColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f),
                unfocusedLabelColor = MaterialTheme.colors.onSurface,
                focusedIndicatorColor = color.value
            ),
            label = {
                Text(text = hint)
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_info_24),
                    tint = MaterialTheme.colors.error,
                    modifier = Modifier.alpha(
                        alpha = alpha.value
                    ),
                    contentDescription = "erro info"
                )
            }
        )
        if (error) {
            Text(
                text = message,
                color = MaterialTheme.colors.error,
                fontSize = 16.sp,
                fontFamily = zillaSlab,
                modifier = modifier
            )
        }
    }
}

@Composable
fun LoadingDialog(
    context: Context,
    username: String
) {
    Dialog(onDismissRequest = {
        Toast.makeText(context, "Hello $username", Toast.LENGTH_LONG).show()
    }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

//to do
@Composable
fun MessageContainer() {

}

