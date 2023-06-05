package com.example.jeepni.feature.authentication

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.*
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.White
import com.example.jeepni.core.ui.theme.quicksandFontFamily
import com.example.jeepni.feature.authentication.signup.SignUpEvent
import com.example.jeepni.feature.authentication.signup.SignUpViewModel
import com.example.jeepni.util.UiEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    val bgImage = if(isSystemInDarkTheme()){R.drawable.bg_dark}else{R.drawable.bg_light}

    JeepNiTheme() {
        Surface(
            modifier = Modifier.fillMaxSize()
        ){
            Box(
                modifier = with (Modifier){
                    fillMaxSize()
                        .paint(
                            painterResource(id = bgImage),
                            contentScale = ContentScale.Crop)
                }
            ){
                Container(0.9f) {
                    BackIconButton {
                        viewModel.onEvent(SignUpEvent.OnBackClicked)
                    }
                    Text(
                        text = "Sign up",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = quicksandFontFamily
                    )
                    Column(
                        horizontalAlignment = Alignment.Start
                    ){
                        JeepNiTextField (
                            modifier = Modifier.fillMaxWidth().height(65.dp),
                            label = "Email",
                            value = viewModel.email,
                            onValueChange = {viewModel.onEvent(SignUpEvent.OnEmailChange(it))},
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
                        )


//                        OutlinedTextField(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(65.dp),
//                            label = {Text("Email", fontFamily = quicksandFontFamily)},
//                            value = viewModel.email,
//                            onValueChange = {viewModel.onEvent(SignUpEvent.OnEmailChange(it))},
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
//                            shape = RoundedCornerShape(20),
//                            colors = TextFieldDefaults.textFieldColors(
//                                containerColor = MaterialTheme.colorScheme.onBackground
//                            )
//                        )
                        JeepNiTextField (
                            modifier = Modifier.fillMaxWidth().height(65.dp),
                            label = "Password",
                            value = viewModel.password,
                            onValueChange = {viewModel.onEvent(SignUpEvent.OnPasswordChange(it))},
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
//                        OutlinedTextField(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(65.dp),
//                            label = {Text("Password")},
//                            value = viewModel.password,
//                            onValueChange = {viewModel.onEvent(SignUpEvent.OnPasswordChange(it))},
//                            visualTransformation = PasswordVisualTransformation(),
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
//                            textStyle = TextStyle(
//                                fontFamily = quicksandFontFamily,
//                            ),
//                            shape = RoundedCornerShape(20),
//                            colors = TextFieldDefaults.textFieldColors(
//                                containerColor = MaterialTheme.colorScheme.onBackground
//                            )
//                        )
//                        OutlinedTextField(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(65.dp),
//                            label = {Text("Confirm Password")},
//                            value = viewModel.confirmPassword,
//                            onValueChange = {viewModel.onEvent(SignUpEvent.OnReEnterPasswordChange(it))},
//                            visualTransformation = PasswordVisualTransformation(),
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
//                            shape = RoundedCornerShape(20),
//                            colors = TextFieldDefaults.textFieldColors(
//                                containerColor = MaterialTheme.colorScheme.onBackground
//                            )
//                        )
                        JeepNiTextField (
                            modifier = Modifier.fillMaxWidth().height(65.dp),
                            label = "Re-enter Password",
                            value = viewModel.confirmPassword,
                            onValueChange = {viewModel.onEvent(SignUpEvent.OnReEnterPasswordChange(it))},
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        Row (modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = viewModel.isAgreeToTerms,
                                onCheckedChange = { viewModel.onEvent(SignUpEvent.OnAgreeTerms(it)) }
                            )
                            Text(text = stringResource(R.string.agree), fontSize = 14.sp, fontFamily = quicksandFontFamily)
                            TextButton(
                                contentPadding = PaddingValues(start = 4.dp, end = 1.dp),
                                onClick = {
                                    //TODO : open terms and conditions dialog
                                    viewModel.onEvent(SignUpEvent.OnShowTermsAndConditions)
                                }
                            ) {
                                Text(
                                    text = stringResource(R.string.terms),
                                    fontFamily = quicksandFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(text = ".", fontSize = 14.sp, fontFamily = quicksandFontFamily)
                        }
                    }
                    Column {
                        SolidButton(
                            onClick = {
                                viewModel.onEvent(SignUpEvent.OnCreateAccountClicked)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.create),
                                fontFamily = quicksandFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        SolidButton(
                            bgColor = MaterialTheme.colorScheme.onBackground,
                            contentColor =  MaterialTheme.colorScheme.background,
                            onClick = {
                                /*TODO: sign up with GOOGLE ACCOUNT */
                                viewModel.onEvent(SignUpEvent.OnCreateAccountWithGoogleClicked)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.create_google),
                                fontFamily = quicksandFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Row (modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        JeepNiText(stringResource(R.string.has_account))
                        TextButton(
                            contentPadding = PaddingValues(start = (3.5).dp),
                            onClick = {
                                viewModel.onEvent(SignUpEvent.OnLogInClicked)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.log_in),
                                fontWeight = FontWeight.Bold,
                                fontFamily = quicksandFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}

val JeepNiIcons = Icons.Filled
@Composable
fun TermsAndConditions(
    onClick : () -> Unit
){
    Container(height = 0.9f) {
        IconButton(onClick = onClick) {

        }
        Text(
            stringResource(R.string.terms),
            Modifier.fillMaxWidth(0.6f)
        )
        Text(
            text = stringResource(R.string.terms1),
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .verticalScroll(
                    rememberScrollState()
                )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            SolidButton(width = 0.45f,
                onClick = {}) {
                Row{
                    JeepNiIcons.Close
                    Text(stringResource(R.string.decline))
                }
            }
            SolidButton(width = 0.82f,
                onClick = {}) {
                Row{
                    JeepNiIcons.Check
                    Text(stringResource(R.string.accept))
                }
            }
        }
    }
}