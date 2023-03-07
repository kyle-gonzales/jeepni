package com.example.jeepni

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jeepni.ui.theme.JeepNiTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainActivityLayout() {
    JeepNiTheme {
        // A surface container using the 'background' color from the theme
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        var drivingMode by remember { mutableStateOf(false) }
        Scaffold (
            scaffoldState = scaffoldState,
            topBar = { TopActionBar(
                scaffoldState = scaffoldState,
                coroutineScope = coroutineScope,
                toggleDrivingMode = {
                    drivingMode = it
                    return@TopActionBar drivingMode
                })
            },
            drawerContent = {Menu()},
            drawerGesturesEnabled = true,
            content = {
                if (drivingMode) {
                    DrivingModeOnContent(paddingValues = it)
                } else {
                    DrivingModeOffContent(paddingValues = it)
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
                .background(Color.Green),
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
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Off")
    }
}

@Composable
fun TopActionBar(scaffoldState: ScaffoldState, coroutineScope : CoroutineScope, toggleDrivingMode : (Boolean) -> Boolean) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var distance = 12132.0
    var distanceState by remember { mutableStateOf(convertDistanceToString(distance)) }

    var timeInMillis = 3999999L
    var timeState by remember { mutableStateOf(convertMillisToTime(timeInMillis)) }

    var dMode by remember { mutableStateOf(false) }

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
//                        Toast.makeText(context, "Menu Icon Clicked", Toast.LENGTH_SHORT).show()
                            coroutineScope.launch { scaffoldState.drawerState.open() }
                        }
                    }) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                Switch(
                    checked = dMode,
                    onCheckedChange = {
                        toggleDrivingMode(it)
                        dMode = it
                    },
                    enabled = true,
                    colors = SwitchDefaults.colors()
                )
            }
        )
    }
}
@Composable
fun Menu() {
    Column() {
        Text("ALGOFIRST Menu")
    }
}
/*
* helper functions
*/
fun convertMillisToTime(timeInMillis: Long) : String {

    val res = if (timeInMillis >= 3600000L) {
        val hours = (timeInMillis / 3600000).toInt()
        val rem : Int = timeInMillis.toInt() % 3600000
        val minutes : Int = rem / 60000
        "$hours hr $minutes min"
    } else {
        val minutes : Int = timeInMillis.toInt() / 60000
        "$minutes min"
    }
    return res
}

fun convertDistanceToString(distance: Double): String {
    val res = if (distance >= 1000) {
        val km = (distance / 1000).toInt()
        val m : Int = distance.toInt() % 1000
        "$km km $m m"
    } else {
        "${(distance.toInt() % 1000)} m"
    }
    return res
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MainActivityLayout()
}

enum class DrivingModeWidgetState {
    ON,
    OFF
}
