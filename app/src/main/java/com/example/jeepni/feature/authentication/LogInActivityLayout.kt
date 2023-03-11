package com.example.jeepni

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.White
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.jeepni.core.ui.BackIconButton
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.SolidButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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
        BackIconButton {
            val intent = Intent(logInContext, WelcomeActivity2::class.java)
            logInContext.startActivity(intent)
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
                    loginUser(logInContext, phoneNumber, password)
                }
            ) {
                Text("Log In")
            }
            SolidButton(
                Black, White,
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

private fun loginUser(baseContext: Context, email: String, password: String) {
    val auth = Firebase.auth


    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(baseContext, "Signed in successfully", Toast.LENGTH_SHORT).show()
                baseContext.startActivity(Intent(baseContext, MainActivity::class.java))
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(baseContext, "Error signing in", Toast.LENGTH_SHORT).show()
            }
        }

}