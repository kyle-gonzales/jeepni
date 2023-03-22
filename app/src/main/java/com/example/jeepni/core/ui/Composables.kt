package com.example.jeepni.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.jeepni.R
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.White
import com.example.jeepni.core.ui.theme.quicksandFontFamily

@Composable
fun Gradient(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxSize(),
    ){
        content()
    }
}

@Composable
fun Container(
    // provides structure for login, signup, terms & conditions
    height:Float,
    content: @Composable () -> Unit
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
fun Logo(
    width: Dp = 100.dp,
    height: Dp = 100.dp
){
    Image(
        painter = painterResource(id = R.drawable.samplelogo),
        contentDescription = "JeepNi Logo",
        modifier = Modifier
            .width(width)
            .height(height)
    )
}

@Composable
fun JeepNiText (
    text : String,
    modifier : Modifier =  Modifier,
    color : Color = MaterialTheme.colorScheme.onSurface,
    fontSize: TextUnit = 14.sp,
    fontStyle: FontStyle = FontStyle.Normal,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Medium
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        textAlign = textAlign,
        fontWeight = fontWeight,
        fontFamily = quicksandFontFamily,
    )
}
@Composable
fun SolidButton(
    bgColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    width: Float = 1f,
    onClick : () -> Unit,
    content: @Composable () -> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth(width)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(36),
        colors = ButtonDefaults.buttonColors(bgColor, contentColor)
    ){
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JeepNiTextField(
    modifier : Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon : @Composable (() -> Unit)? = null,
    trailingIcon : @Composable (() -> Unit)? = null,
    isError : Boolean = false,
    errorMessage : String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine : Boolean = true,
    ){
    Column(
        modifier = Modifier
        .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = {Text(text = label, fontFamily = quicksandFontFamily)},
            isError = isError,
            shape = RoundedCornerShape(36),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = TextStyle(fontFamily = quicksandFontFamily),
            singleLine = singleLine,
            supportingText = {
                if (isError) {
                    Text(
                        text = errorMessage, //! convert to state
                        color = MaterialTheme.colorScheme.error,
                        fontFamily = quicksandFontFamily,
                    )
                }
            },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}

@Composable
fun BackIconButton(
    onClick : () -> Unit
){
    IconButton(onClick = { onClick() })
    {
        Icon(Icons.Filled.ArrowBack, contentDescription = null )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun T() {

    var text by remember {
        mutableStateOf("")
    }
    var width = 10.dp

    var isValid by remember {
        mutableStateOf(false)
    }

    var icon = null
    JeepNiTheme {
        Surface(
            modifier = Modifier
                .padding(16.dp) //remove this after
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = text,
                    onValueChange = { text = it },
                    label = {Text(text = "label", fontFamily = quicksandFontFamily)},
                    isError = isValid,
                    leadingIcon = null,
                    shape = RoundedCornerShape(36),
                    textStyle = TextStyle(fontFamily = quicksandFontFamily),
                    supportingText = {
                        if (isValid) {
                            Text(
                                text = "Invalid Phone Number", //! convert to state
                                color = MaterialTheme.colorScheme.error,
                                fontFamily = quicksandFontFamily,
                            )
                        }
                    }
                )
            }

        }
    }

}

@Composable
fun AnalyticsCard(
    date: String,
    revenue: String,
    expenses: String,
    content: @Composable () -> Unit
) {
    Row {
        Card(
            shape = RoundedCornerShape(size = 5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = date,
                    fontSize = 15.sp,
                    fontFamily = quicksandFontFamily,
                    fontWeight = FontWeight.Bold
                )
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp)
                ) {
                    Text(
                        text = revenue,
                        fontSize = 15.sp,
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = expenses,
                        fontSize = 15.sp,
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Light
                    )}
                content()
            }
        }
    }
}

//@Composable
//fun FilterIconButton(
//    onClick : () -> Unit,
//    menuItems: List<String>,
//    onMenuItemClick: (String) -> Unit
//){
//    var expanded by remember { mutableStateOf(false) }
//
//    IconButton(onClick = { expanded = true })
//    {
//        Icon(Icons.Filled.Menu, contentDescription = "Menu")
//    }

//    DropdownMenu(
//        expanded = expanded,
//        onDismissRequest = { expanded = false })
//    {
//        menuItems.forEach{ item ->
//            DropdownMenuItem(
//               onClick = {
//                   onMenuItemClick(item)
//                   expanded = false
//               }){
//                Text(item)
//            }
//        }
//    }
//}

