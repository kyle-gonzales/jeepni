package com.example.jeepni.feature.analytics

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    viewModel: AnalyticsViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit
) {
    var mutableDate by remember { mutableStateOf("") }
    var mutableRevenue by remember { mutableStateOf("") }
    var mutableExpenses by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when(event) {
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
                    titledesc = "Content",
                        onPopBackStack
                ) },
                content = { paddingValues ->
            LazyColumn(contentPadding = paddingValues){
                item {
                    AnalyticsCard(
                        date = "date",
                        revenue = "revenue",
                        expenses = "expenses"
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
    onPopBackStack: () -> Unit
){
    Surface (
        contentColor = Color.White,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
    ){
        TopAppBar(
            title = {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = titledesc)
                }
            },
            navigationIcon = {
                BackIconButton (onClick = onPopBackStack)
                }
        )
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun PreviewLayout(){
//    AnalyticsScreen(
//        "date", "revenue", "expenses"
//    )
//}