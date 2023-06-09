package com.example.jeepni.feature.authentication

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.*
import com.example.jeepni.core.ui.theme.*
import com.example.jeepni.feature.authentication.login.LogInEvent
import com.example.jeepni.feature.authentication.login.LogInViewModel
import com.example.jeepni.util.UiEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    viewModel : LogInViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
){
    val context = LocalContext.current
    val bgImage = if(isSystemInDarkTheme()){R.drawable.bg_dark}else{R.drawable.bg_light}

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    JeepNiTheme {
        Surface {
            Box(
                modifier = with (Modifier){
                    fillMaxSize()
                        .paint(
                            painterResource(id = bgImage),
                            contentScale = ContentScale.Crop)
                },
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier.fillMaxSize(0.85f),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ){
                    Column(){
                        BackIconButton {
                            viewModel.onEvent(LogInEvent.OnBackPressed)
                        }
                        Spacer(Modifier.height(40.dp))
                        Text(
                            text = "Log in",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = quicksandFontFamily
                        )
                    }
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        JeepNiTextField (
                            modifier = Modifier.fillMaxWidth().height(65.dp),
                            label = "Email",
                            value = viewModel.email,
                            onValueChange = {viewModel.onEvent(LogInEvent.OnEmailChange(it))},
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
                            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                        )
                        JeepNiTextField (
                            modifier = Modifier.fillMaxWidth().height(65.dp),
                            label = "Password",
                            value = viewModel.password,
                            onValueChange = {viewModel.onEvent(LogInEvent.OnPasswordChange(it))},
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                        )
                        TextButton(
                            onClick = {
                                //TODO : implement forgot password
                                viewModel.onEvent(LogInEvent.OnForgotPasswordClicked)
                            },
                            contentPadding = PaddingValues(0.dp)
                        ){
                            Text("Forgot Password",
                                fontFamily = quicksandFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                        SolidButton(
                            onClick = {
                                viewModel.onEvent(LogInEvent.OnLogInClicked)
                            }
                        ) {
                            Text(
                                "Log in",
                                fontFamily = quicksandFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        SolidButton(
                            bgColor = MaterialTheme.colorScheme.onBackground,
                            contentColor =  MaterialTheme.colorScheme.background,
                            onClick = {
                                //TODO: implement login with GOOGLE ACCOUNT
                                viewModel.onEvent(LogInEvent.OnLogInWithGoogle)
                            }) {
                            Text(
                                "Log in with Google",
                                fontFamily = quicksandFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            JeepNiText(stringResource(R.string.no_account))
                            TextButton(
                                onClick = {
                                    viewModel.onEvent(LogInEvent.OnSignUpClicked)
                                },
                                contentPadding = PaddingValues(start = 3.5.dp)
                            ){
                                Text(stringResource(R.string.sign_up), fontFamily = quicksandFontFamily, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

