package com.example.jeepni.feature.initial_checkup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.core.ui.DatePicker
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.util.UiEvent

@Composable
fun InitialCheckupScreen(
    viewModel: InitialCheckupViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect {event ->
            when(event) {
                UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiEvent.ShowSnackBar -> {

                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    JeepNiTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Select Jeepney parts in need for replacement or repair")
                Column(modifier = Modifier.fillMaxWidth()) {
                    DatePicker("Date of last oil change")
                    DatePicker("Date of last LTFRB inspection")
                    DatePicker("Date of last LTO inspection")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Checkbox(
                                checked = false,
                                onCheckedChange = {}
                            )
                            Text(
                                text = "Tire",
                                modifier = Modifier.padding(start = 50.dp)
                            )
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Checkbox(
                                checked = false,
                                onCheckedChange = {}
                            )
                            Text(
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
                                checked = false,
                                onCheckedChange = {}
                            )
                            Text(
                                text = "Side Mirror",
                                modifier = Modifier.padding(start = 50.dp)
                            )
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Checkbox(
                                checked = false,
                                onCheckedChange = {}
                            )
                            Text(
                                text = "Seat Belt",
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
                                checked = false,
                                onCheckedChange = {}
                            )
                            Text(
                                text = "Wiper",
                                modifier = Modifier.padding(start = 50.dp)
                            )
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Checkbox(
                                checked = false,
                                onCheckedChange = {}
                            )
                            Text(
                                text = "Battery",
                                modifier = Modifier.padding(start = 50.dp)
                            )
                        }
                    }
                }
                SolidButton(
                    onClick = {viewModel.onEvent(InitialCheckupEvent.OnSaveClicked)}
                ) {
                    Text("Save Checkup Info")
                }
            }
        }
    }
}

@Preview
@Composable
fun ICPreview(){
    InitialCheckupScreen(onNavigate = {}){
    }
}