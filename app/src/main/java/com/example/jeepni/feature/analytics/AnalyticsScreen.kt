package com.example.jeepni.feature.analytics

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.core.ui.AnalyticsCard
import com.example.jeepni.core.ui.JeepNiBasicAppBar
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
                 else -> {

                 }
            }
        }
    }

    JeepNiTheme {
        Surface {
            Scaffold(
                topBar = {
                    JeepNiBasicAppBar(
                        title = "Analytics",
                        onPopBackStack = {  viewModel.onEvent(AnalyticsEvent.OnBackPressed)  }
                    )
                },
                content = { paddingValues ->
                    LazyColumn(contentPadding = paddingValues) {
                        items(analytics ?: emptyList()) { item ->
                            AnalyticsCard(
                                date = item.date,
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

