package com.example.jeepni

import android.content.Intent
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
import com.example.jeepni.core.ui.BackIconButton
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.White


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
                }
            ) {
                Text(stringResource(R.string.create))
            }
            SolidButton(
                Black, White,
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