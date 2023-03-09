package com.example.jeepni

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jeepni.ui.theme.Black
import com.example.jeepni.ui.theme.White

@Preview
@Composable
fun SignUpActivityLayout() {
    val context = LocalContext.current
    var agreeToTerms by remember { mutableStateOf(false) }


    Container(0.9f) {
        BackIconButton()
        Text(
            stringResource(R.string.sign_up_welcome2),
            Modifier.fillMaxWidth(0.6f)
        )
        Column {
            CustomTextField()
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
                onClick = {}
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