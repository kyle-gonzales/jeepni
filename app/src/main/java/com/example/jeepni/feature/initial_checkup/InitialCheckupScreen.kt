package com.example.jeepni.feature.initial_checkup

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.Container
import com.example.jeepni.core.ui.DatePicker
import com.example.jeepni.core.ui.JeepNiText
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.quicksandFontFamily
import com.example.jeepni.util.UiEvent
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialCheckupScreen(
    viewModel: InitialCheckupViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiEvent.ShowSnackBar -> {
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
    JeepNiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            Container(0.9f) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    TextButton(
                        onClick = { viewModel.onEvent(InitialCheckupEvent.OnSkipPressed) }
                    ){
                        Text(
                            text = "Skip",
                            fontFamily = quicksandFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(Modifier.height(40.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(
                        text = "Initial Checkup",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = quicksandFontFamily
                    )
                    Spacer(Modifier.height(40.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DatePicker(
                            label = " Date of last oil change ",
                            pickedDate = viewModel.oilChangeDate,
                            onChange = {
                                viewModel.onEvent(
                                    InitialCheckupEvent.OnOilChangeDateChange(it)
                                )
                            },
                            dateValidator = {
                                it.isBefore(LocalDate.now()) || it.isEqual(LocalDate.now()) // disable future dates
                            }
                        )
                        Spacer(Modifier.height(16.dp))
                        DatePicker(
                            label = " Date of last LTFRB inspection ",
                            pickedDate = viewModel.ltfrbDate,
                            onChange = {
                                viewModel.onEvent(
                                    InitialCheckupEvent.OnLtfrbDateChange(it)
                                )
                            },
                            dateValidator = {
                                it.isBefore(LocalDate.now()) || it.isEqual(LocalDate.now()) // disable future dates
                            }
                        )
                        Spacer(Modifier.height(16.dp))
                        DatePicker(
                            label = " Date of last LTO inspection ",
                            pickedDate = viewModel.ltoDate,
                            onChange = {
                                viewModel.onEvent(
                                    InitialCheckupEvent.OnLtoDateChange(it)
                                )
                            },
                            dateValidator = {
                                it.isBefore(LocalDate.now()) || it.isEqual(LocalDate.now()) // disable future dates
                            }
                        )
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Select Jeepney parts to replace or repair",
                            fontWeight = FontWeight.Bold,
                            fontFamily = quicksandFontFamily
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Checkbox(
                                    checked = viewModel.isTireEnabled,
                                    onCheckedChange = {
                                        viewModel.onEvent(
                                            InitialCheckupEvent.OnTireClicked(it)
                                        )
                                    }
                                )
                                JeepNiText(
                                    text = "Tire",
                                    modifier = Modifier.padding(start = 50.dp)
                                )
                            }
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Checkbox(
                                    checked = viewModel.isEngineEnabled,
                                    onCheckedChange = {
                                        viewModel.onEvent(
                                            InitialCheckupEvent.OnEngineClicked(it)
                                        )
                                    }
                                )
                                JeepNiText(
                                    text = "Engine",
                                    modifier = Modifier.padding(start = 50.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Checkbox(
                                    checked = viewModel.isMirrorsEnabled,
                                    onCheckedChange = {
                                        viewModel.onEvent(
                                            InitialCheckupEvent.OnMirrorsClicked(it)
                                        )
                                    }
                                )
                                JeepNiText(
                                    text = "Side Mirror",
                                    modifier = Modifier.padding(start = 50.dp)
                                )
                            }
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Checkbox(
                                    checked = viewModel.isSeatbeltEnabled,
                                    onCheckedChange = {
                                        viewModel.onEvent(
                                            InitialCheckupEvent.OnSeatbeltClicked(it)
                                        )
                                    }
                                )
                                JeepNiText(
                                    text = "Seatbelt",
                                    modifier = Modifier.padding(start = 50.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Checkbox(
                                    checked = viewModel.isWipersEnabled,
                                    onCheckedChange = {
                                        viewModel.onEvent(
                                            InitialCheckupEvent.OnWipersClicked(it)
                                        )
                                    }
                                )
                                JeepNiText(
                                    text = "Wipers",
                                    modifier = Modifier.padding(start = 50.dp)
                                )
                            }
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Checkbox(
                                    checked = viewModel.isBatteryEnabled,
                                    onCheckedChange = {
                                        viewModel.onEvent(
                                            InitialCheckupEvent.OnBatteryClicked(it)
                                        )
                                    }
                                )
                                JeepNiText(
                                    text = "Battery",
                                    modifier = Modifier.padding(start = 50.dp)
                                )
                            }
                        }
                    }
                    SolidButton(onClick = { viewModel.onEvent(InitialCheckupEvent.OnSaveClicked) }) {
                        Text("Save", fontFamily = quicksandFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    onSkipPressed: () -> Unit,
) {
    Surface(
        contentColor = Color.White,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    JeepNiText(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            actions = {
                IconButton(onClick = onSkipPressed) {
                    Icon(painterResource(id = R.drawable.black_skip_next_24), "Skip Initial Checkup")
                }
            }
        )
    }
}

@Preview
@Composable
fun ICPreview(){
    DatePicker(
        label = " Date of last oil",
        pickedDate = LocalDate.now(),
        onChange = {
        },
        dateValidator = {true}
    )
}