package com.example.jeepni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jeepni.ui.theme.*

val gradientColors = listOf(Purple, Teal)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JeepNiTheme{
                Welcome()
            }
        }
    }
}

@Composable
fun Gradient(
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    gradientColors
                )
            ),
    ){
        content()
    }
}

@Composable
fun Welcome(){
    Gradient{
        Box(
            Modifier.fillMaxSize(),
            Alignment.Center
        ){
            Logo()
        }
    }
}

@Composable
fun Logo(){
    Image(
        painterResource(id = R.drawable.samplelogo),
        "Sample JeepNi Logo",
        Modifier.fillMaxSize(0.25f)
    )
}

@Composable
fun SolidButton(
    bgcolor: Color = White,
    textcolor: Color = Black,
    width: Float = 1f,
    content: @Composable () () -> Unit
){
    Button(
        onClick ={},
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth(width)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(bgcolor, textcolor)
    ){
        content()
    }
}

@Composable
fun GradientButton(
    content: @Composable () () -> Unit
){
    Button(
        onClick ={},
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .background(brush = Brush.horizontalGradient(gradientColors))
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(10.dp),
    ){
        content()
    }
}
@Composable
fun Container(
    height:Float,
    content: @Composable () () -> Unit
) {
    Gradient{
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(height),
            ){
                Column(
                    Modifier.fillMaxSize(),
                    Arrangement.SpaceEvenly,
                ){
                    content()
                }
            }
        }
    }
}

@Composable
fun LoginOrSignup(){
    Container(0.6f){
        Logo()
        Text(
            stringResource(R.string.welcome1),
            Modifier.fillMaxWidth(0.6f)
        )
        Column{
            SolidButton() {
                Text(stringResource(R.string.sign_up))
            }
            SolidButton(Black, White) {
                Text(stringResource(R.string.log_in))
            }
        }
    }
}

@Composable
fun CustomTextField(){
    Box(
        Modifier
            .background(color = White)
            .height(50.dp)
            .width(50.dp)
    ){
        Text("text field ni")
    }
}

@Composable
fun IconButton(){
    Box(
        Modifier
            .background(color = White)
            .height(50.dp)
            .width(50.dp)
    ){
        Text("icon button ni")
    }
}
@Composable
fun LogIn(){
    Container(0.9f){
        IconButton()
        Text(
            stringResource(R.string.welcome1),
            Modifier.fillMaxWidth(0.6f)
        )
        Column{
            CustomTextField()
            CustomTextField()
        }
        TextButton(
            onClick = {}
        ){
            Text(text = stringResource(R.string.forgot))
        }
        Column{
            SolidButton() {
                Text(stringResource(R.string.log_in))
            }
            SolidButton(Black, White) {
                Text(stringResource(R.string.log_in_google))
            }
        }
        Row{
            Text(stringResource(R.string.no_account))
            TextButton(
                onClick = {}
            ){
                Text(stringResource(R.string.sign_up))
            }
        }
    }
}

@Composable
fun SignUp(){
    val agreeToTerms = remember { mutableStateOf(true) }
    Container(0.9f){
        IconButton()
        Text(
            stringResource(R.string.sign_up_welcome2),
            Modifier.fillMaxWidth(0.6f)
        )
        Column{
            CustomTextField()
            CustomTextField()
            CustomTextField()
        }
        Row {
            Checkbox(
                checked = agreeToTerms.value,
                onCheckedChange = { agreeToTerms.value = it}
            )
            Text(stringResource(R.string.agree))
            TextButton(
                onClick = {}
            ){
                Text(stringResource(R.string.sign_up))
            }
        }
        Column{
            SolidButton() {
                Text(stringResource(R.string.create))
            }
            SolidButton(Black, White) {
                Text(stringResource(R.string.create_google))
            }
        }
        Row{
            Text(stringResource(R.string.has_account))
            TextButton(
                onClick = {}
            ){
                Text(stringResource(R.string.log_in))
            }
        }
    }
}

val JeepNiIcons = Icons.Filled
@Composable
fun TermsAndConditions(){
    Container(height = 0.9f) {
        IconButton()
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
            SolidButton(width = 0.45f) {
                Row{
                    JeepNiIcons.Close
                    Text(stringResource(R.string.decline))
                }
            }
            SolidButton(width = 0.82f) {
               Row{
                   JeepNiIcons.Check
                   Text(stringResource(R.string.accept))
               }
           } 
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Textfield(){
    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember { mutableStateOf("") }
        Text(
            text = "Hello! $name",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = name,
            label = { Text(text = "Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                name = it
            }
        )
    }
}

/*
@Preview(showSystemUi = true)
@Composable
fun WelcomePreview() {
    JeepNiTheme{
        Welcome()
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginOrSignupPreview() {
    JeepNiTheme{
        LoginOrSignup()
    }
}

@Preview(showSystemUi = true)
@Composable
fun LogInPreview() {
    JeepNiTheme{
        LogIn()
    }
}
@Preview(showSystemUi = true)
@Composable
fun SignUpPreview() {
    JeepNiTheme{
        SignUp()
    }
}

 */
@Preview(showSystemUi = true)
@Composable
fun TermsAndConditionsPreview() {
    JeepNiTheme{
        TermsAndConditions()
    }
}