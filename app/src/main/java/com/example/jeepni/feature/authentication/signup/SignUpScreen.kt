package com.example.jeepni.feature.authentication.signup

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.*
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.quicksandFontFamily
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
        Surface{
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
                    Column() {
                        BackIconButton {
                            viewModel.onEvent(SignUpEvent.OnBackClicked)
                        }
                        Spacer(Modifier.height(40.dp))
                        Text(
                            text = "Sign up",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = quicksandFontFamily
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom
                    ){
                        JeepNiTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(65.dp),
                            label = "Email",
                            value = viewModel.email,
                            onValueChange = {viewModel.onEvent(SignUpEvent.OnEmailChange(it))},
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
                            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                        )
                        JeepNiTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(65.dp),
                            label = "Password",
                            value = viewModel.password,
                            onValueChange = {viewModel.onEvent(SignUpEvent.OnPasswordChange(it))},
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                        )
                        JeepNiTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(65.dp),
                            label = "Re-enter Password",
                            value = viewModel.confirmPassword,
                            onValueChange = {viewModel.onEvent(SignUpEvent.OnReEnterPasswordChange(it))},
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background),
                        )
                        Row (
                            modifier = Modifier.offset(x = -12.dp, y = 2.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
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
                        Spacer(Modifier.height(20.dp))
                        SolidButton(
                            onClick = {
                                viewModel.onEvent(SignUpEvent.OnCreateAccountClicked)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.create),
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
                                /*TODO: sign up with GOOGLE ACCOUNT */
                                viewModel.onEvent(SignUpEvent.OnCreateAccountWithGoogleClicked)
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.create_google),
                                fontFamily = quicksandFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Row (modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            JeepNiText(stringResource(R.string.has_account))
                            TextButton(
                                contentPadding = PaddingValues(start = 1.dp),
                                onClick = {
                                    viewModel.onEvent(SignUpEvent.OnLogInClicked)
                                }
                            ) {
                                Text(
                                    text = stringResource(R.string.log_in),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = quicksandFontFamily,
                                )
                            }
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
        BackIconButton(
            onClick = onClick
        )
        Text(
            stringResource(R.string.terms),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = quicksandFontFamily
        )
        Text(
            text = stringResource(R.string.terms1),
            fontFamily = quicksandFontFamily,
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .verticalScroll(
                    rememberScrollState()
                ),
            textAlign = TextAlign.Justify
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            SolidButton(
                width = 0.45f,
                onClick = {},
                bgColor = MaterialTheme.colorScheme.onBackground,
                contentColor =  MaterialTheme.colorScheme.background,
            ) {
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