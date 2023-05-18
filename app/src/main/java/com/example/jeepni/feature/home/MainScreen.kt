package com.example.jeepni.feature.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.MainActivity
import com.example.jeepni.R
import com.example.jeepni.core.ui.JeepNiTextField
import com.example.jeepni.core.ui.PermissionDialog
import com.example.jeepni.core.ui.theme.*
import com.example.jeepni.feature.home.nullstrings.NullReplacements
import com.example.jeepni.util.LocationPermissionTextProvider
import com.example.jeepni.util.TestTags.TestTags
import com.example.jeepni.util.TestTags.TestTags.CONTENT_MAP
import com.example.jeepni.util.UiEvent
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigate : (UiEvent.Navigate) -> Unit,
    viewModel : MainViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current

    val dialogQueue = viewModel.visiblePermissionDialogQueue
    
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        viewModel.onPermissionResult(
            permission = Manifest.permission.ACCESS_FINE_LOCATION,
            isGranted = isGranted
        )
        if (isGranted) {
            viewModel.onEvent(MainEvent.OnToggleDrivingMode(true))
        }
    }
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var isAllGranted = true
        permissions.keys.forEach { permission ->
            viewModel.onPermissionResult(
                permission = permission,
                isGranted = permissions[permission] == true
            )
            isAllGranted = permissions[permission] == true && isAllGranted
        }
        if (isAllGranted) {
            viewModel.onEvent(MainEvent.OnToggleDrivingMode(true))
        }
    }

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
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
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
        val menuItems = listOf( /*TODO: may need to refactor this*/
            MenuItem(R.drawable.black_chart_24, "Charts") { viewModel.onEvent(MainEvent.OnChartsClicked) },
            MenuItem(R.drawable.black_tools_24, "JeepNi Check-Up") {
                viewModel.onEvent(MainEvent.OnCheckUpClicked)
            }
        )
        Surface {
                Menu(
                    drawerState = drawerState,
                    email = viewModel.user?.email,
                    onProfileClicked = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        viewModel.onEvent(MainEvent.OnProfileClicked)
                    },
                    scope = coroutineScope,
                    menuItems = menuItems,
                ) {
                    Scaffold (
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        topBar = { TopActionBar(
                            drawerState = drawerState,
                            drivingMode = viewModel.drivingMode,
                            scope = coroutineScope,
                            toggleDrivingMode ={

                                val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)

                                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                    viewModel.onEvent(MainEvent.OnToggleDrivingMode(it))
                                } else {
                                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                }
                            },
                            distance = viewModel.distanceState,
                            time = viewModel.timeState,
                            onDistanceChange = {viewModel.onEvent(MainEvent.OnDistanceChange(it)) },
                            onTimeChange = {viewModel.onEvent(MainEvent.OnTimeChange(it))}
                        ) },

                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    viewModel.onEvent(MainEvent.OnOpenAddDailyStatDialog(true))
                                },
                                modifier = Modifier
                                    .testTag(TestTags.BUTTON_LOG_DAILY_ANALYTICS)

                            ) {
                                Icon(
                                    painterResource(id = R.drawable.black_dollar_24),
                                    contentDescription = null
                                )
                            }
                        },
                        floatingActionButtonPosition = FabPosition.End,
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                if (viewModel.drivingMode) {
                                    DrivingModeOnContent(
                                        paddingValues = it,
                                        cameraPositionState = viewModel.cameraPositionState,
                                        targetPosition = viewModel.targetPosition,
                                        onMapLoaded = {} //viewModel::onMapLoaded
                                    )
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
                                            viewModel.onEvent(MainEvent.OnDeleteDailyStatClick)
                                        },
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .testTag(TestTags.BUTTON_DELETE_DAILY_STATS)
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
                val activity = LocalContext.current as MainActivity //apparently this is not a memory leak!
                dialogQueue.reversed().forEach { permission ->
                    PermissionDialog(
                        permissionTextProvider = when (permission) {
                            Manifest.permission.ACCESS_FINE_LOCATION -> {
                                LocationPermissionTextProvider()
                            }
                            else -> {
                                return@forEach
                            }
                        },
                        isPermanentlyDeclined = ! shouldShowRequestPermissionRationale(activity, permission) ,
                        onDismiss = viewModel::dismissDialog,
                        onConfirm = {
                            multiplePermissionResultLauncher.launch(
                                arrayOf(permission)
                            )
                        },
                        onGoToAppSettings = activity::openAppSettings
                    )

                }
            }
        }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
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
                Text("Log Daily Analytics", fontFamily = quicksandFontFamily)
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
                    JeepNiTextField(
                        value = salary,
                        onValueChange = { onSalaryChange(it) },
                        label = "Salary",
                        singleLine = true,
                        leadingIcon = { Icon(painterResource(id = R.drawable.white_dollar_24), null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        isError = !isValidSalary,
                        errorMessage = "Invalid Input"
                    )
                    Text(
                        modifier = Modifier
                            .padding(0.dp, 8.dp)
                            .wrapContentWidth(),
                        text = "Enter the amount you spent on fuel for today: ",
                        textAlign = TextAlign.Start
                    )
                    JeepNiTextField(
                        value = fuelCost,
                        onValueChange = {onFuelCostChange(it)},
                        label = "Fuel Cost",
                        singleLine = true,
                        leadingIcon = {Icon(painterResource(id = R.drawable.white_dollar_24), null)},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = !isValidFuelCost,
                        errorMessage = "Invalid Input"
                    )
                }
            }
        )
    }
}

@Composable
fun DrivingModeOnContent(
    paddingValues : PaddingValues,
    targetPosition : LatLng,
    cameraPositionState: CameraPositionState,
    onMapLoaded : () -> Unit,
) {

    val cebuBounds = LatLngBounds.Builder()
        .include(LatLng(9.386076, 123.258371))
        .include(LatLng(11.399790, 124.135218))
        .build()

    val holePoints = listOf(
        cebuBounds.northeast,
        LatLng(cebuBounds.northeast.latitude, cebuBounds.southwest.longitude),
        cebuBounds.southwest,
        LatLng(cebuBounds.southwest.latitude, cebuBounds.northeast.longitude)
    )

    Surface {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .testTag(CONTENT_MAP),
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .padding(8.dp),
                cameraPositionState = cameraPositionState,
                onMapLoaded = onMapLoaded,
                googleMapOptionsFactory = {
                    val cameraPosition = CameraPosition.Builder()
                        .target(LatLng(10.3157, 123.8854)) // set to a point within cebuBounds
                        .zoom(10f)
                        .build()
                    GoogleMapOptions()
                        .maxZoomPreference(14.0f)
                        .minZoomPreference(9.0f)
                        .latLngBoundsForCameraTarget(cebuBounds)
                        .camera(cameraPosition)
                },
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = true,
                    compassEnabled = true,
                    zoomControlsEnabled = true
                ),
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions(
                        if (isSystemInDarkTheme())
                            JsonMapStyle.DARK_MAP_STYLE
                        else
                            JsonMapStyle.LIGHT_MAP_STYLE
                    ),
                    latLngBoundsForCameraTarget = cebuBounds,
                    isMyLocationEnabled = false, // set to true to turn on blue dot
                    maxZoomPreference = 25.0f,
                    minZoomPreference = 9.0f,
                ),
            ) {
//                Polygon(
//                    points = listOf(
//                        LatLng(85.0,90.0),
//                        LatLng(85.0,0.1),
//                        LatLng(85.0,-90.0),
//                        LatLng(85.0,-179.9),
//                        LatLng(0.0,-179.9),
//                        LatLng(-85.0,-179.9),
//                        LatLng(-85.0,-90.0),
//                        LatLng(-85.0,0.1),
//                        LatLng(-85.0,90.0),
//                        LatLng(-85.0,179.9),
//                        LatLng(0.0,179.9),
//                        LatLng(85.0,179.9),
//                    ),
//                    strokeWidth = 0F,
//                    fillColor = Color.White,
//                    holes = listOf(holePoints),
//                    zIndex = 10000.0f,
//                )
                Marker(
                    state = MarkerState(position = targetPosition),
                    title = "You", //TODO: give the name of the driver?
                )
            }
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
}

@Composable
fun DrivingModeOffContent(paddingValues: PaddingValues) {
    Surface {
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
                    Icon(Icons.Filled.LocationOn, contentDescription = null)
                    Text(
                        modifier = Modifier.padding(4.dp, 0.dp),
                        text = distance,
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Icon(painterResource(R.drawable.white_timer_24), contentDescription = null)
                    Text(
                        modifier = Modifier.padding(4.dp, 0.dp),
                        text = time,
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            },
            modifier = Modifier,
            navigationIcon = {
                IconButton(
                    onClick = {
                        scope.launch { scope.launch { drawerState.open() } }
                    },
                    modifier = Modifier
                        .testTag(TestTags.BUTTON_MAIN_DRAWER)
                ) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                Row(modifier = Modifier.padding(8.dp,0.dp)) {
                    Switch(
                        checked = drivingMode,
                        onCheckedChange = { toggleDrivingMode(it) },
                        modifier = Modifier
                            .testTag(TestTags.BUTTON_DRIVING_MODE),
                        enabled = true,
                        colors = SwitchDefaults.colors()
                    )
                }
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuContent(
    email : String?,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onProfileClicked : () -> Unit,
    menuItems : List<MenuItem>
) {

    ModalDrawerSheet(
        modifier = Modifier
            .padding(0.dp, 0.dp, 60.dp, 0.dp)
            .testTag(TestTags.CONTENT_DRAWER)
            .fillMaxHeight(),
    ) {
        Surface {
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                ) {
                    Box ( // backdrop
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .background(dark_primaryContainer),
                        contentAlignment = Alignment.BottomStart
                    ) {
                    }
                    Row (
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                            .align(Alignment.BottomStart),
                        verticalAlignment = Alignment.Top
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_pic), // should become state
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(6.dp, MaterialTheme.colorScheme.surface, CircleShape)
                                .clickable {
                                    onProfileClicked()
                                },
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            email
                                ?: NullReplacements.TEXT_EMAIL_NOT_FOUND, /* TODO: show user name instead */
                            modifier = Modifier
                                .padding(12.dp, 8.dp)
                                .testTag(TestTags.CONTENT_DRAWER_EMAIL)
                                .clickable {
                                    onProfileClicked()
                                },
                            fontFamily = quicksandFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = light_onPrimary
                        )
                    }
                }
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Spacer(Modifier.height(12.dp))
                    menuItems.forEach {  menuItem ->
                        NavigationDrawerItem(
                            label = { Text(menuItem.title, fontFamily = quicksandFontFamily, fontSize = 18.sp) },
                            icon = {  Icon(painterResource(id = menuItem.icon), null)  },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                                menuItem.onClick()
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }

                }

            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(
    drawerState: DrawerState,
    email : String?,
    onProfileClicked : () -> Unit,
    scope: CoroutineScope,
    menuItems : List<MenuItem>,
    content : @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        modifier = Modifier.background(Color.Transparent),
        drawerState = drawerState,
        drawerContent = { MenuContent(email = email,  drawerState = drawerState, scope = scope, onProfileClicked = onProfileClicked, menuItems = menuItems) },
        gesturesEnabled = drawerState.isOpen,
        content = content
    )
}