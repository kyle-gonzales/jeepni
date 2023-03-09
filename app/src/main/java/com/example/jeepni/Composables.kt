package com.example.jeepni

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jeepni.ui.theme.*

@Composable
fun Gradient(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
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
    content: @Composable () () -> Unit
){
    Button(
        onClick = {onClick()},
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
            .background(brush = Brush.horizontalGradient(gradientColors))
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(10.dp),
    ){
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    input: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean,
    label: @Composable() (() -> Unit),
){
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = onValueChange,
            label = label,
            isError = !isValid,
        )
        if (!isValid) {
            Text(
                text = "Invalid email",
                color = Color.Red
            )
        }
    }
}

@Composable
fun BackIconButton(){
    IconButton(onClick = { /*TODO*/ })
    {
        Icon(Icons.Filled.ArrowBack, contentDescription = null )
    }
}