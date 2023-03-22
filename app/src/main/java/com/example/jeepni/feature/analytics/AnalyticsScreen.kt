package com.example.jeepni.feature.analytics

import androidx.compose.material.*
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.core.ui.AnalyticsCard
import com.example.jeepni.core.ui.BackIconButton
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    viewModel : AnalyticsViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit,
) {

    val analytics by viewModel.analytics.collectAsState(initial = null) // this is the list that is used in the lazy column
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
            }
        }
    }

    JeepNiTheme {
        Surface {
            Scaffold(
                topBar = {
                    AppBar(
                        titledesc = "Analytics",
                        onPopBackStack = {  viewModel.onEvent(AnalyticsEvent.OnBackPressed)  }
                    )
                },
                content = { paddingValues ->
                    LazyColumn(contentPadding = paddingValues) {
                        items(analytics ?: emptyList()) { item ->
                            AnalyticsCard(
                                date = "2-22-2222",
                                revenue = "Revenue: " + item.salary.toString(),
                                expenses = "Fuel Cost: " + item.fuelCost.toString()
                            ) {
                            }
                        }
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    titledesc: String,
    onPopBackStack: () -> Unit,
//    onMenuItems: () -> Unit
) {
    val menuItems = listOf(
        "Revenue",
        "Fuel Expenses",
        "Time Travelled",
        "Distance Travelled"
    )

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
                    Text(text = titledesc)
                }
            },
            navigationIcon = {
                BackIconButton(onClick = onPopBackStack)
            }
        )
    }
}
