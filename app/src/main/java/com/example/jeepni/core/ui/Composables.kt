package com.example.jeepni.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.jeepni.R
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.White

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
@Composable
fun CustomTextField(
    input: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean,
    label: @Composable() (() -> Unit),
){
    Column(modifier = Modifier
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
                text = "Invalid Phone Number",
                color = Color.Red
            )
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDown(
    label:String,
    expanded:Boolean,
    value:String,
    onClickIcon: () -> Unit,
    onSizeChange: () -> Unit,
    onSelected: () -> Unit,
    size:Size,
    items:List<String>
){
    val icon = if(expanded){
        Icons.Filled.KeyboardArrowDown
    }else{
        Icons.Filled.KeyboardArrowUp
    }

    Column{
        Box{
            OutlinedTextField(
                value = value,
                onValueChange = {},
                label = { Text(text = label)},
                trailingIcon = {
                    Icon(
                        icon,
                        contentDescription = null,
                        Modifier.clickable{
                            onClickIcon
                        }
                    )
                },
                modifier = Modifier.onGloballyPositioned {
                    onSizeChange
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    onClickIcon
                },
                modifier = Modifier.width(
                    with(LocalDensity.current){
                        size.width.toDp()
                    }
                )
            ) {
                items.forEachIndexed() { index, s ->
                    DropdownMenuItem(
                        text = {Text(text = s)},
                        onClick = {
                            onSelected
                            onClickIcon
                        }
                    )
                }
            }
        }
    }
}
