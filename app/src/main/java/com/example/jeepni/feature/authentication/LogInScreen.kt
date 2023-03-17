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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.White
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.BackIconButton
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.JeepNiTypography
import com.example.jeepni.util.UiEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    viewModel : LogInViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit,
){
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when(event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
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
                Text(
                    stringResource(R.string.welcome_back),
                    Modifier.fillMaxWidth(0.6f)
                )
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    // TODO: TextFieldColors need to be changed. Contrast ratio is so bad
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = {Text("Email")},
                        value = viewModel.email,
                        onValueChange = {viewModel.onEvent(LogInEvent.OnEmailChange(it))},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
                        )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = {Text("Password")},
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
                    Text("Forgot Password",
                        color = Color.White)
                }
                Column{
                    SolidButton(
                        onClick = {
                            //TODO: implement login with phone number
                            viewModel.onEvent(LogInEvent.OnLogInClicked)
                        }
                    ) {
                        Text("Log In")
                    }
                    SolidButton(
                        Black, White,
                    onClick = {
                        //TODO: implement login with GOOGLE ACCOUNT
                        viewModel.onEvent(LogInEvent.OnLogInWithGoogle)
                    }) {
                        Text("Log In with Google")
                    }
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(stringResource(R.string.no_account))
                    TextButton(
                        onClick = {
                            viewModel.onEvent(LogInEvent.OnSignUpClicked)
                        }
                    ){
                        Text(stringResource(R.string.sign_up))
                    }
                }
            }

        }

    }
}

//private fun loginUser(baseContext: Context, email: String, password: String) {
//    val auth = Firebase.auth
//
//
//    auth.signInWithEmailAndPassword(email, password)
//        .addOnCompleteListener {
//            if (it.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
//                Toast.makeText(baseContext, "Signed in successfully", Toast.LENGTH_SHORT).show()
//                baseContext.startActivity(Intent(baseContext, MainActivity::class.java))
//            } else {
//                // If sign in fails, display a message to the user.
//                Toast.makeText(baseContext, "Error signing in", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//}