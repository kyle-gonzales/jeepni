package com.example.jeepni.feature.authentication

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
            Container(0.9f){
                BackIconButton {
                    viewModel.onEvent(LogInEvent.OnBackPressed)
                }
                JeepNiText(
                    stringResource(R.string.welcome_back),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    JeepNiTextField (
                        modifier = Modifier.fillMaxWidth(),
                        label = "Email",
                        value = viewModel.email,
                        onValueChange = {viewModel.onEvent(LogInEvent.OnEmailChange(it))},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
                        )
                    JeepNiTextField (
                        modifier = Modifier.fillMaxWidth(),
                        label = "Password",
                        value = viewModel.password,
                        onValueChange = {viewModel.onEvent(LogInEvent.OnPasswordChange(it))},
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }
                TextButton(
                    onClick = {
                        //TODO : implement forgot password
                        viewModel.onEvent(LogInEvent.OnForgotPasswordClicked)
                    }
                ){
                    Text("Forgot Password", fontFamily = quicksandFontFamily, fontWeight = FontWeight.Bold)
                }
                Column {
                    SolidButton(
                        onClick = {
                            viewModel.onEvent(LogInEvent.OnLogInClicked)
                        }
                    ) {
                        Text("Log In", fontFamily = quicksandFontFamily, fontWeight = FontWeight.Bold)
                    }
                    SolidButton(
                        Black, White,
                    onClick = {
                        //TODO: implement login with GOOGLE ACCOUNT
                        viewModel.onEvent(LogInEvent.OnLogInWithGoogle)
                    }) {
                        Text("Log In with Google", fontFamily = quicksandFontFamily, fontWeight = FontWeight.Bold)
                    }
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    JeepNiText(stringResource(R.string.no_account))
                    TextButton(
                        onClick = {
                            viewModel.onEvent(LogInEvent.OnSignUpClicked)
                        },
                    ){
                        Text(stringResource(R.string.sign_up), fontFamily = quicksandFontFamily, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

