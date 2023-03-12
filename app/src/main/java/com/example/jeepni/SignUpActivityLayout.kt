package com.example.jeepni

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jeepni.ui.theme.Black
import com.example.jeepni.ui.theme.White
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


@Preview
@Composable
fun SignUpActivityLayout() {
    val context = LocalContext.current
    var agreeToTerms by remember { mutableStateOf(false) }
    var isValidNumber by remember { mutableStateOf(true) }
    var isValidPassword by remember { mutableStateOf(true) }
    var isValidPassword1 by remember { mutableStateOf(true) }
    var number by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password1 by remember { mutableStateOf("") }

    Container(0.9f) {
        BackIconButton {
            val intent = Intent(context, WelcomeActivity2::class.java)
            context.startActivity(intent)
        }
        Text(
            stringResource(R.string.sign_up_welcome2),
            Modifier.fillMaxWidth(0.6f)
        )
        Column {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Phone Number")},
                value = number,
                onValueChange = {number = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Password")},
                value = password,
                onValueChange = {password = it},
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {Text("Re-enter Password")},
                value = password1,
                onValueChange = {password1 = it},
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
            )
        }
        Row (modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Checkbox(
                checked = agreeToTerms,
                onCheckedChange = { agreeToTerms = it }
            )
            Text(stringResource(R.string.agree))
            TextButton(
                onClick = {}
            ) {
                Text(stringResource(R.string.terms))
            }
        }
        Column {
            SolidButton(
                onClick = {
                    /*TODO: sign up*/
                    Toast.makeText(context, "creating account...", Toast.LENGTH_SHORT).show()
                    //createAccountEmail(context, number, password, password1)
                    createAccountEmail(context, number, password, password1)

                }
            ) {
                Text(stringResource(R.string.create))
            }
            SolidButton(Black, White,
            onClick = {
                /*TODO: sign up with GOOGLE ACCOUNT */
                Toast.makeText(context, "creating account with google...", Toast.LENGTH_SHORT).show()

            }) {
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
                    val intent = Intent(context, LogInActivity::class.java)
                    context.startActivity(intent)
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


// IDK HOW TO MAKE A SEPARATE FILE IN KOTLIN

private fun createAccountEmail(baseContext: Context, email: String, password: String, confirmation: String){

    //  Check if form is valid
    //    if(!validateForm(email, password, confirmation)){
    //        return
    //    }
    if (password != confirmation){
        return
    }

    val auth = Firebase.auth
    auth.createUserWithEmailAndPassword(email.trim(), password.trim())
        .addOnCompleteListener {
            if (it.isSuccessful) {
                //Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                Toast.makeText(baseContext, "Registered user successfully", Toast.LENGTH_SHORT).show()
                updateUI(baseContext, user)

            } else {
                //val errorCode = (it.exception as FirebaseAuthException).errorCode
                //val errorMessage = authErrors[errorCode] ?: R.string.error_login_default_error
                //Log.w(TAG, "createUserWithEmail:failure", it.exception)
                Toast.makeText(baseContext, "Registration unsuccessful", Toast.LENGTH_SHORT).show()

            }
        }
}

private fun createAccountNumber(baseContext: Context, phoneNumber: String, password: String, confirmation: String){
    // TODO: finish this function
    // -> https://firebase.google.com/docs/auth/android/phone-auth?authuser=1&hl=en <-


    //  Check if form is valid
    //    if(!validateForm(email, password, confirmation)){
    //        return
    //    }
    if (password != confirmation){
        return
    }

    val auth = Firebase.auth
    auth.setLanguageCode("en")
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            updateUI(baseContext, credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            //Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            //Log.d(TAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            //storedVerificationId = verificationId
            //resendToken = token
        }
    }
//    val options = PhoneAuthOptions.newBuilder(auth)
//        .setPhoneNumber(phoneNumber)       // Phone number to verify
//        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
//        .setActivity(Context)                 // Activity (for callback binding)
//        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//        .build()
//    PhoneAuthProvider.verifyPhoneNumber(options)
}
private fun updateUI(baseContext: Context, user: FirebaseUser?){
    baseContext.startActivity(Intent(baseContext, LogInActivity::class.java))

}
private fun updateUI(baseContext: Context, user: PhoneAuthCredential?){
    baseContext.startActivity(Intent(baseContext, LogInActivity::class.java))

}
