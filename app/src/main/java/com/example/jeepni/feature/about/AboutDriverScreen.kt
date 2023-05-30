package com.example.jeepni.feature.about

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R

import com.example.jeepni.core.ui.CustomDropDown
import com.example.jeepni.core.ui.JeepNiAlertDialog
import com.example.jeepni.core.ui.JeepNiTextField
import com.example.jeepni.core.ui.SolidButton

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

    val snackbarHostState = remember { SnackbarHostState() }

    JeepNiTheme {
        Surface {
            Scaffold (
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = {
                    Surface(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp // can be changed
                    ) {
                        TopAppBar(
                            title = {
                                Row (
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.padding(4.dp, 0.dp),
                                        text = "About you"
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        viewModel.onEvent(AboutDriverEvent.OnBackPress)
                                    }
                                ) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            }
                        )
                    }
                },
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(36.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            JeepNiTextField(
                                value = viewModel.firstName,
                                onValueChange = { viewModel.onEvent(AboutDriverEvent.OnFirstNameChange(it)) },

                                label = "First Name",
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                isError = !viewModel.isValidFirstName //TODO: implement check for valid first name
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
                            SolidButton(onClick = { viewModel.onEvent(AboutDriverEvent.OnSaveDetailsClick) }) {
                                Text("Save Details", fontFamily = quicksandFontFamily, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            )
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