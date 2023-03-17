package com.example.jeepni.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jeepni.R
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.White
import com.example.jeepni.core.ui.theme.quicksandFontFamily
import com.example.jeepni.isValidDecimal

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
fun SolidButton(
    bgColor: Color = White,
    contentColor: Color = Black,
    width: Float = 1f,
//    isClicked: Boolean = false,
//    onButtonChange: (Boolean) -> Unit,
    onClick : () -> Unit,
    content: @Composable () -> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth(width)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(bgColor, contentColor)
    ){
        content()
    }
}

@Composable
fun GradientButton(
//    isClicked: Boolean,
//    onButtonChange: (Boolean) -> Unit,
    onClick : () -> Unit,
    content: @Composable () -> Unit
){
    Button(
        onClick ={onClick()},
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(10.dp),
    ){
        content()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun T() {
    var inFocus by remember {
        mutableStateOf(false)
    }
    var text by remember {
        mutableStateOf("")
    }
    var width = 10.dp

    var isValid by remember {
        mutableStateOf(false)
    }
    JeepNiTheme(
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp) //remove this after
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
            ) {
                if (!inFocus)
                    width = 1.dp
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = text,
                    onValueChange = { text = it },
                    label = {Text("label")},
                    isError = isValid,
                    shape = RoundedCornerShape(50),
                    textStyle = TextStyle(fontFamily = quicksandFontFamily)
                )
                if (isValid) {
                    Text(
                        text = "Invalid Phone Number", //! convert to state
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JeepNiTextField(
    input: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean,
    label: @Composable() (() -> Unit),
){
    JeepNiTheme {
        Surface {
            Column(modifier = Modifier
                .fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = input,
                    onValueChange = onValueChange,
                    label = label,
                    isError = !isValid,
                )
                if (!isValid) {
                    Text(
                        text = "Invalid Phone Number",
                        color = Color.Red
                    )
                }
            }
        }
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