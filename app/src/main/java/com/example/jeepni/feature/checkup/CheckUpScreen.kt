@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.jeepni.feature.checkup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.BackIconButton
import com.example.jeepni.core.ui.ComponentCard
import com.example.jeepni.core.ui.JeepNiText
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.feature.initial_checkup.InitialCheckupEvent
import com.example.jeepni.util.UiEvent
import com.example.jeepni.util.Constants
import com.example.jeepni.util.Constants.ICON_MAP

@Composable
fun CheckUpScreen (
    viewModel: CheckUpViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val componentsList = Constants.COMPONENTS
    val viewmodel = mapOf<String, CheckUpEvent>(
    "Tires" to CheckUpEvent.OnTiresClicked,
    "Oil Change" to CheckUpEvent.OnOilChangeClicked,
    "Side Mirrors" to CheckUpEvent.OnSideMirrorsClicked,
    "LTFRB Check" to CheckUpEvent.OnLTFRBCheckClicked,
    "LTO Check" to CheckUpEvent.OnLTOCheckClicked,
    "Wipers" to CheckUpEvent.OnWipersClicked,
    "Engine" to CheckUpEvent.OnEngineClicked,
    "Seatbelt" to CheckUpEvent.OnSeatbeltClicked,
    "Battery" to CheckUpEvent.OnBatteryClicked,
    )

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
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

    JeepNiTheme() {
        Surface {
            Scaffold(
                topBar = {
                    AppBar(
                        titledesc = "JeepNi! Check-up",
                        onPopBackStack = {  viewModel.onEvent(CheckUpEvent.OnBackPressed)  }
                    )
                },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.inverseOnSurface)
                    ){
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = paddingValues,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            item {
                                Box(modifier = Modifier
                                    .padding(8.dp)
                                    .clickable { }) {
                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(MaterialTheme.colorScheme.outlineVariant)
                                            .requiredWidth(164.5.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Column(modifier = Modifier.padding(20.dp)) {
                                            Icon(
                                                painter = painterResource(R.drawable.add_48px),
                                                contentDescription = null,
                                                modifier = Modifier.size(45.dp)
                                            )
                                        }
                                    }
                                }
                            }
                            items(componentsList) {
                                    components ->
                                Box(modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        viewModel.onEvent(viewmodel[components]!!)
                                    }){
                                    ComponentCard(
                                        component = components,
                                        date = "mm/dd/yyyy",
                                        alarm = "3 months",
                                        icon = painterResource(ICON_MAP[components]!!)
                                    )
                                }
                            }
                        }
                    }
                })
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    titledesc: String,
    onPopBackStack: () -> Unit,
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
                    JeepNiText(text = titledesc)
                }
            },
            navigationIcon = {
                BackIconButton(onClick = onPopBackStack)
            }
        )
    }
}
@Preview(showSystemUi = true)
@Composable
fun Preview(){
    CheckUpScreen(onNavigate = {}) {
    }
}