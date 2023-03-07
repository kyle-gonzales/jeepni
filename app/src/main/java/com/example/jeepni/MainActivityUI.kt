package com.example.jeepni

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jeepni.ui.theme.JeepNiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityLayout() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var drivingMode by remember { mutableStateOf(false) }
    var isLogDailyAnalyticsDialogOpen by remember {mutableStateOf(false) }

    var salary by rememberSaveable { mutableStateOf("") }
    var fuelCost by rememberSaveable { mutableStateOf("")}
    var isValidSalary by remember { mutableStateOf(isValidDecimal(salary))}
    var isValidFuelCost by remember {mutableStateOf(isValidDecimal(fuelCost))}

    var loginId by remember {mutableStateOf("test@email.com")}

    JeepNiTheme {
        // A surface container using the 'background' color from the theme
        Scaffold (
            scaffoldState = scaffoldState,
            topBar = { TopActionBar(
                drivingMode = drivingMode ,
                scaffoldState = scaffoldState,
                coroutineScope = coroutineScope,
                toggleDrivingMode = { drivingMode = it })
            },
            drawerContent = {Menu(loginId)},
            drawerGesturesEnabled = true,
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    isLogDailyAnalyticsDialogOpen = true
                }) {
                    Icon(painterResource(id = R.drawable.black_dollar_24), contentDescription = null)
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            content = {
                if (drivingMode) {
                    DrivingModeOnContent(paddingValues = it)
                } else {
                    DrivingModeOffContent(paddingValues = it)
                }
            }
        )

    }
    if (isLogDailyAnalyticsDialogOpen) {
        LogDailyStatDialog(salary, fuelCost, isValidSalary, isValidFuelCost,
            onDialogChange = { isLogDailyAnalyticsDialogOpen = it },
            onSalaryTextChange = {
                salary = it
                isValidSalary = isValidDecimal(it)
                },
            onFuelCostTextChange = {
                fuelCost = it
                isValidFuelCost = isValidDecimal(it)
            }
        )
    }
}

@Composable
fun LogDailyStatDialog(
    salary: String,
    fuelCost : String,
    isValidSalary : Boolean,
    isValidFuelCost : Boolean,
    isDialogOpen: Boolean = true,
    onDialogChange: (Boolean) -> Unit,
    onSalaryTextChange: (String) -> Unit,
    onFuelCostTextChange: (String) -> Unit) {

    val context = LocalContext.current

    if (isDialogOpen) {
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = {
                onDialogChange(false)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        /*save to database*/
                        Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                        onDialogChange(false)
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
                        onDialogChange(false)
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
                        onValueChange = { onSalaryTextChange(it) },
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
                        onValueChange = {onFuelCostTextChange(it)},
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
            .padding(paddingValues),

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
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Driving Mode is Off")
    }
}

@Composable
fun TopActionBar(drivingMode : Boolean, scaffoldState: ScaffoldState, coroutineScope : CoroutineScope, toggleDrivingMode : (Boolean) -> Unit) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var distance = 12132.0
    var distanceState by remember { mutableStateOf(convertDistanceToString(distance))}

    var timeInMillis = 3999999L
    var timeState by remember { mutableStateOf(convertMillisToTime(timeInMillis)) }

    Surface(
        contentColor = Color.White,
        color = MaterialTheme.colors.primarySurface,
        elevation = 8.dp // can be changed
    ) {
        TopAppBar(
            title = {
                Icon(Icons.Filled.LocationOn, contentDescription = null)
                Text(
                    modifier = Modifier.padding(4.dp, 0.dp),
                    text = distanceState
                )
                Icon(painterResource(R.drawable.white_timer_24), contentDescription = null)
                Text(
                    modifier = Modifier.padding(4.dp, 0.dp),
                    text = timeState
                )
            },
            modifier = Modifier,
            navigationIcon = {
                IconButton(
                    onClick = {
                        scope.launch {
                            coroutineScope.launch { scaffoldState.drawerState.open() }
                        }
                    }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                Switch(
                    checked = drivingMode,
                    onCheckedChange = { toggleDrivingMode(it) },
                    enabled = true,
                    colors = SwitchDefaults.colors()
                )
            }
        )
    }
}
@Composable
fun Menu(
    email : String,
) {
    val context = LocalContext.current
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
            Text(email)
        }
        Button(onClick = { /*TODO : logout of email */ Toast.makeText(context, "logged out!", Toast.LENGTH_SHORT).show() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Log Out")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MainActivityLayout()
}
