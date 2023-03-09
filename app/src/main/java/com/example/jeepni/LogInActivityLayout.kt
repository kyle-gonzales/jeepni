package com.example.jeepni

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jeepni.ui.theme.Black
import com.example.jeepni.ui.theme.White
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


//@Composable
//fun SignUpLogInTextFieldColors () : TextFieldColors {
//
//}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LogInActivityLayout(){
    val logInContext = LocalContext.current

    var phoneNumber by rememberSaveable{
        mutableStateOf("")
    }
    var password by rememberSaveable{
        mutableStateOf("")
    }
    Container(0.9f){
        BackIconButton()
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
                label = {Text("Phone Number")},
                value = phoneNumber,
                onValueChange = {phoneNumber = it},
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii, imeAction = ImeAction.Next),
                )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {Text("Password")},
                value = password,
                onValueChange = {password = it},
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

        }
        TextButton(
            onClick = {
                //TODO : implement forgot password
            }
        ){
            Text("Forgot Password",
                color = Color.White)
        }
        Column{
            SolidButton(
                onClick = {
                    //TODO: implement login with phone number
                }
            ) {
                Text("Log In")
            }
            SolidButton(Black, White,
            onClick = {
                //TODO: implement login with GOOGLE ACCOUNT
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
                    logInContext.startActivity(Intent(logInContext, SignUpActivity::class.java))
                }
            ){
                Text(stringResource(R.string.sign_up))
            }
        }
    }
}
