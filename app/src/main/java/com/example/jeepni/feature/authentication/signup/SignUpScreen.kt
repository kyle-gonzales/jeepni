package com.example.jeepni.feature.authentication

import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.BackIconButton
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.JeepNiText
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.White
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

    JeepNiTheme() {
        Surface {

            Container(0.9f) {
                BackIconButton {
                    viewModel.onEvent(SignUpEvent.OnBackClicked)
                }
                JeepNiText(
                    stringResource(R.string.sign_up_welcome2),
                    Modifier.fillMaxWidth(0.6f),
                    fontSize = 16.sp,
                )
                Column {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {Text("Email")},
                        value = viewModel.email,
                        onValueChange = {viewModel.onEvent(SignUpEvent.OnEmailChange(it))},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {Text("Password")},
                        value = viewModel.password,
                        onValueChange = {viewModel.onEvent(SignUpEvent.OnPasswordChange(it))},
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {Text("Confirm Password")},
                        value = viewModel.confirmPassword,
                        onValueChange = {viewModel.onEvent(SignUpEvent.OnReEnterPasswordChange(it))},
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
                    )
                }
                Row (modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Checkbox(
                        checked = viewModel.isAgreeToTerms,
                        onCheckedChange = { viewModel.onEvent(SignUpEvent.OnAgreeTerms(it)) }
                    )
                    JeepNiText(stringResource(R.string.agree))
                    TextButton(
                        onClick = {
                            //TODO : open terms and conditions dialog
                            viewModel.onEvent(SignUpEvent.OnShowTermsAndConditions)
                        }
                    ) {
                        JeepNiText(stringResource(R.string.terms),
                            color = MaterialTheme.colorScheme.primary)
                    }
                }
                Column {
                    SolidButton(
                        onClick = {
                            viewModel.onEvent(SignUpEvent.OnCreateAccountClicked)
                        }
                    ) {
                        JeepNiText(stringResource(R.string.create),
                            fontSize = 16.sp,
                            color = Color.Black)
                    }
                    SolidButton(
                        Black, White,
                        onClick = {
                            /*TODO: sign up with GOOGLE ACCOUNT */
                            viewModel.onEvent(SignUpEvent.OnCreateAccountWithGoogleClicked)
                        }
                    ) {
                        JeepNiText(stringResource(R.string.create_google),
                            fontSize = 16.sp,
                            color = Color.White)
                    }
                }
                Row (modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    JeepNiText(stringResource(R.string.has_account),
                        fontSize = 16.sp)
                    TextButton(
                        modifier = Modifier
                            .padding(0.dp),
                        onClick = {
                            viewModel.onEvent(SignUpEvent.OnLogInClicked)
                        }
                    ) {
                        JeepNiText(stringResource(R.string.log_in),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary)
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
        JeepNiText(
            stringResource(R.string.terms),
            Modifier.fillMaxWidth(0.6f),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
        JeepNiText(
            text = stringResource(R.string.terms1),
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .verticalScroll(
                    rememberScrollState()
                ),
            fontSize = 16.sp
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            SolidButton(width = 0.45f,
                onClick = {}) {
                Row{
                    JeepNiIcons.Close
                    Text(stringResource(R.string.decline), fontSize = 16.sp)
                }
            }
            SolidButton(width = 0.82f,
                onClick = {}) {
                Row{
                    JeepNiIcons.Check
                    Text(stringResource(R.string.accept), fontSize = 16.sp)
                }
            }
        }
    }
}