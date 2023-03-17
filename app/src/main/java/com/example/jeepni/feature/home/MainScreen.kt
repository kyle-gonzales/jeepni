package com.example.jeepni.feature.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    email : String,
    onNavigate : (UiEvent.Navigate) -> Unit,
    viewModel : MainViewModel = hiltViewModel(),
) {
//    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    var drivingMode by remember { mutableStateOf(false) }
//    var isLogDailyAnalyticsDialogOpen by remember {mutableStateOf(false) }
//
//    var salary by rememberSaveable { mutableStateOf("") }
//    var fuelCost by rememberSaveable { mutableStateOf("")}
//    var isValidSalary by remember { mutableStateOf(isValidDecimal(salary))}
//    var isValidFuelCost by remember {mutableStateOf(isValidDecimal(fuelCost))}
//
//    var loginId by remember {mutableStateOf("test@email.com")}
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {// don't subscribe to UI event flow every time the UI updates
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiEvent.PopBackStack -> {

                }
                is UiEvent.ShowSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(MainEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    JeepNiTheme {
        Menu(
            drawerState = drawerState,
            email = email,
            onLogOutClick = {viewModel.onEvent(MainEvent.OnLogOutClick)}
        ) {
            Scaffold (
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = { TopActionBar(
                    drawerState = drawerState,
                    drivingMode = viewModel.drivingMode,
                    scope = coroutineScope,
                    toggleDrivingMode ={ viewModel.onEvent(MainEvent.OnToggleDrivingMode(it)) },
                    distance = viewModel.distanceState,
                    time = viewModel.timeState,
                    onDistanceChange = {viewModel.onEvent(MainEvent.OnDistanceChange(it)) },
                    onTimeChange = {viewModel.onEvent(MainEvent.OnTimeChange(it))}
                ) },

                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        viewModel.onEvent(MainEvent.OnOpenAddDailyStatDialog(true))
                    }) {
                        Icon(painterResource(id = R.drawable.black_dollar_24), contentDescription = null)
                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (viewModel.drivingMode) {
                            DrivingModeOnContent(paddingValues = it)
                        } else {
                            DrivingModeOffContent(paddingValues = it)
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            FloatingActionButton(
                                onClick = {
                                    /*TODO: Delete the current daily log*/
                                    viewModel.onEvent(MainEvent.OnDeleteDailyStatClick)
                                },
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Icon(Icons.Filled.Delete, null)
                            }
                        }
                    }
                }
            )
        }
        if (viewModel.isAddDailyStatDialogOpen) {
            LogDailyStatDialog(
                salary = viewModel.salary,
                onSalaryChange = {viewModel.onEvent(MainEvent.OnSalaryChange(it))},
                fuelCost = viewModel.fuelCost,
                onFuelCostChange = {viewModel.onEvent(MainEvent.OnFuelCostChange(it))},
                isValidSalary = viewModel.isValidSalary,
                isValidFuelCost = viewModel.isValidFuelCost,
                isDialogOpen = viewModel.isAddDailyStatDialogOpen,
                onDialogOpenChange = {viewModel.onEvent(MainEvent.OnOpenAddDailyStatDialog(it))},
                onSave = { salary, fuelCost ->
                    viewModel.onEvent(MainEvent.OnSaveDailyAnalyticClick(salary.toDouble(), fuelCost.toDouble()))}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDailyStatDialog(
    salary: String,
    onSalaryChange : (String) -> Unit,
    fuelCost : String,
    onFuelCostChange : (String) -> Unit,
    isValidSalary : Boolean,
    isValidFuelCost : Boolean,
    isDialogOpen: Boolean = true,
    onDialogOpenChange : (Boolean) -> Unit,
    onSave : (String, String) -> Unit
 ) {
    val context = LocalContext.current

    if (isDialogOpen) {
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = {
                onDialogOpenChange(false)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        /*TODO: add / edit to database*/
                        onSave(salary, fuelCost)
                        onDialogOpenChange(false)
                    },
                    modifier = Modifier
                        .padding(8.dp),
                    content = {
                        Text("Save")
                    }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDialogOpenChange(false)
                    },
                    modifier = Modifier
                        .padding(8.dp),
                    content = {
                        Text("Cancel")
                    }
                )
            },
            title = {
                Text("Log Daily Analytics")
            },
            text = {

                Column (
                    modifier = Modifier,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        modifier = Modifier.padding(0.dp, 8.dp),
                        text = "Enter your earnings for today: ",
                        textAlign = TextAlign.Start
                    )
                    OutlinedTextField(
                        value = salary,
                        placeholder = {Text(salary)},
                        onValueChange = { onSalaryChange(it) },
                        label = {Text("Salary")},
                        singleLine = true,
                        leadingIcon = {Icon(painterResource(id = R.drawable.white_dollar_24), null)},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        isError = !isValidSalary
                        // TODO: parse input to accept commas, no letters in case of external keyboard
                    )
//                    if (!isValidSalary) {
//                        Text(
//                            text = "Invalid Salary",
//                            style = MaterialTheme.typography.caption,
//                            color = MaterialTheme.colors.error,
//                        )
//                    }
                    Text(
                        modifier = Modifier
                            .padding(0.dp, 8.dp)
                            .wrapContentWidth(),
                        text = "Enter the amount you spent on fuel for today: ",
                        textAlign = TextAlign.Start
                    )
                    OutlinedTextField(
                        value = fuelCost,
                        placeholder = {Text(fuelCost)},
                        onValueChange = {onFuelCostChange(it)},
                        label = {Text("Fuel Cost")},
                        singleLine = true,
                        leadingIcon = {Icon(painterResource(id = R.drawable.white_dollar_24), null)},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = !isValidFuelCost
                        // TODO: parse input to accept commas, no letters in case of external keyboard
                    )
                }
            }
        )
    }
}

@Composable
fun DrivingModeOnContent(paddingValues : PaddingValues) {
    Column (
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(0.8f)
                .background(Color.Gray),
            textAlign = TextAlign.Center,
            text = "Map"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(0.2f)
                .background(Color.LightGray),
            horizontalArrangement = Arrangement.Center
        ) {

        }
    }
}
@Composable
fun DrivingModeOffContent(paddingValues: PaddingValues) {
    Column (
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Driving Mode is Off")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopActionBar(
    drawerState: DrawerState,
    drivingMode : Boolean,
    scope : CoroutineScope,
    toggleDrivingMode : (Boolean) -> Unit,
    distance : String,
    time : String,
    onDistanceChange: (String) -> Unit,
    onTimeChange : (String) -> Unit
    ) {
    Surface(
        contentColor = Color.White,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp // can be changed
    ) {
        TopAppBar(
            title = {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp, 0.dp),
                        text = distance
                    )
                    Icon(painterResource(R.drawable.white_timer_24), contentDescription = null)
                    Text(
                        modifier = Modifier.padding(4.dp, 0.dp),
                        text = time
                    )
                }
                Icon(Icons.Filled.LocationOn, contentDescription = null)
            },
            modifier = Modifier,
            navigationIcon = {
                IconButton(
                    onClick = {
                        scope.launch {
                            scope.launch { drawerState.open() }
                        }
                    }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                Row(modifier = Modifier.padding(8.dp,0.dp)) {
                    Switch(
                        checked = drivingMode,
                        onCheckedChange = { toggleDrivingMode(it) },
                        enabled = true,
                        colors = SwitchDefaults.colors()
                    )
                }
            }
        )
    }
}
@Composable
fun MenuContent(
    email : String,
    onLogOutClick : () -> Unit
) {
    val context = LocalContext.current
    Surface {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(email) /*TODO: update info based on login info */
            }
            Button(
                onClick = { onLogOutClick() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Log Out")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(drawerState: DrawerState,
         email : String,
         onLogOutClick: () -> Unit,
        content : @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { MenuContent(email = email, onLogOutClick= onLogOutClick) },
        content = content
    )
}
