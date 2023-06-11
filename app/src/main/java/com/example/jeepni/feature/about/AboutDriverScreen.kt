package com.example.jeepni.feature.about

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.data.model.Jeeps
import com.example.jeepni.core.ui.*
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.quicksandFontFamily
import com.example.jeepni.util.UiEvent
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutDriverScreen(
    viewModel : AboutDriverViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit
) {

    BackHandler {
        viewModel.onEvent(AboutDriverEvent.OnBackPress)
    }
    val bgImage = if(isSystemInDarkTheme()){R.drawable.driver_dark}else{R.drawable.driver_light}
    val routes = viewModel.jeepneyRoutes.mapTo(arrayListOf()) { it.route }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    JeepNiTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ){
            Box(
                modifier = with (Modifier){
                    fillMaxSize()
                        .paint(
                            painterResource(id = bgImage),
                            contentScale = ContentScale.Crop)
                }
            ){
                Container(0.9f) {
                    BackIconButton {
                        viewModel.onEvent(AboutDriverEvent.OnBackPress)
                    }
                    Spacer(Modifier.height(200.dp))
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ){
                        Text(
                            text = "About you",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = quicksandFontFamily
                        )
                        Column {
                            JeepNiTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(65.dp),
                                label = "First Name",
                                value = viewModel.firstName,
                                onValueChange = {viewModel.onEvent(AboutDriverEvent.OnFirstNameChange(it))},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                singleLine = true,
                                isError = !viewModel.isValidFirstName,
                                colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background)
                            )
                            CustomDropDown(
                                label = "Select route",
                                expanded = viewModel.isRouteDropdownClicked,
                                value = viewModel.route,
                                size = viewModel.routeDropdownSize,
                                onClickIcon = {viewModel.onEvent(AboutDriverEvent.OnRouteDropDownClick(it)) },
                                onSizeChange = {viewModel.onEvent(AboutDriverEvent.OnRouteSizeChange(it))}, // how to get it ????
                                onSelected = {viewModel.onEvent(AboutDriverEvent.OnRouteChange(it))},
                                items = routes
                            )
                        }
                        SolidButton(onClick = { viewModel.onEvent(AboutDriverEvent.OnSaveDetailsClick) }) {
                            Text("Save", fontFamily = quicksandFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }

    if (viewModel.isDialogOpen) {
        JeepNiAlertDialog(
            onDismiss = {
                viewModel.onEvent(AboutDriverEvent.OnDialogDismissPress)
            }, onConfirm = {
                viewModel.onEvent(AboutDriverEvent.OnDialogConfirmPress)
            }, icon = {
                Icon(painterResource(R.drawable.logout_white_24), null)
            },
            titleText = "Logout?"
        ) {
            Text("It seems that you haven't completed your user profile... Going back to the previous screen will sign you out",
                fontFamily = quicksandFontFamily)
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewAboutDriverScreen(
) {

    BackHandler {
    }
    val bgImage = if(isSystemInDarkTheme()){R.drawable.driver_dark}else{R.drawable.driver_light}

    JeepNiTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ){
            Box(
                modifier = with (Modifier){
                    fillMaxSize()
                        .paint(
                            painterResource(id = bgImage),
                            contentScale = ContentScale.Crop)
                }
            ){
                Container(0.9f) {
                    BackIconButton {
                    }
                    Spacer(Modifier.height(200.dp))
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ){
                        Text(
                            text = "About you",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = quicksandFontFamily
                        )
                        Column {
                            JeepNiTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(65.dp),
                                label = "First Name",
                                value = "",
                                onValueChange = {},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                singleLine = true,
                                isError = false,
                                colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background)
                            )
                            CustomDropDown(
                                label = "Select route",
                                expanded = false,
                                value = "",
                                size = Size.Zero,
                                onClickIcon = { },
                                onSizeChange = {}, // how to get it ????
                                onSelected = {},
                                items = listOf("A", "B", "C")
                            )
                        }
                        SolidButton(onClick = {  }) {
                            Text("Save", fontFamily = quicksandFontFamily, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
