@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jeepni.feature.checkup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.jeepni.util.UiEvent

@Composable
fun CheckUpScreen (
    viewModel: CheckUpViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

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
                    LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = paddingValues,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                    ) {
                        //will change this
                                item {
                                    Box(modifier = Modifier.padding(8.dp)){
                                        ComponentCard(
                                            component = "LTFRB",
                                            date = "mm/dd/yyyy",
                                            alarm = "3 months",
                                            icon = painterResource(R.drawable.tire_repair)
                                        )
                                    }
                                }
                                item {
                                    Box(modifier = Modifier.padding(8.dp)){
                                        ComponentCard(
                                            component = "Oil Change",
                                            date = "mm/dd/yyyy",
                                            alarm = "3 months",
                                            icon = painterResource(R.drawable.tire_repair)
                                        )
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