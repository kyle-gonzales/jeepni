package com.example.jeepni.feature.authentication

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.BackIconButton
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.White
import com.example.jeepni.util.UiEvent


@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Container(0.9f) {
        BackIconButton {
            viewModel.onEvent(SignUpEvent.OnBackClicked)
        }
        Text(
            stringResource(R.string.sign_up_welcome2),
            Modifier.fillMaxWidth(0.6f)
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
            Text(stringResource(R.string.agree))
            TextButton(
                onClick = {
                    //TODO : open terms and conditions dialog
                }
            ) {
                Text(stringResource(R.string.terms))
            }
        }
        val context = LocalContext.current
        Column {
            SolidButton(
                onClick = {
                    viewModel.onEvent(SignUpEvent.OnCreateAccountClicked)
                }
            ) {
                Text(stringResource(R.string.create))
            }
            SolidButton(
                Black, White,
                onClick = {
                    /*TODO: sign up with GOOGLE ACCOUNT */
                    viewModel.onEvent(SignUpEvent.OnCreateAccountWithGoogleClicked)
                }
            ) {
                Text(stringResource(R.string.create_google))
            }
        }
        Row (modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(stringResource(R.string.has_account))
            TextButton(
                modifier = Modifier
                    .padding(0.dp),
                onClick = {
                    viewModel.onEvent(SignUpEvent.OnLogInClicked)
                }
            ) {
                Text(stringResource(R.string.log_in))
            }
        }
    }
}

val JeepNiIcons = Icons.Filled
@Composable
fun TermsAndConditions(){
    Container(height = 0.9f) {
        IconButton(onClick = {}) {}
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