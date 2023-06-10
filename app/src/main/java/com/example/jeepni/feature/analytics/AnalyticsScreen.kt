package com.example.jeepni.feature.analytics

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.AnalyticsCard
import com.example.jeepni.core.ui.JeepNiBasicAppBar
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.quicksandFontFamily
import com.example.jeepni.util.UiEvent
import com.example.jeepni.util.formatDistanceToString
import com.example.jeepni.util.formatSecondsToTime
import java.text.DecimalFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    viewModel : AnalyticsViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit,
) {

    val analytics by viewModel.analytics.collectAsState(initial = null) // this is the list that is used in the lazy column
    val averageFuelCost by viewModel.averageFuelCost.collectAsState(initial = 0.0)
    val averageSalary by viewModel.averageSalary.collectAsState(initial = 0.0)
    val df = DecimalFormat("#.##")


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
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    Card (
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .padding(8.dp, 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 4.dp)
                                    .weight(.5f)
                            ) {
                                Icon(painterResource(R.drawable.white_lightbulb_24), null)
                                Spacer(Modifier.padding(8.dp, 0.dp))
                                Card (
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                                ) {
                                    Column (Modifier.padding(8.dp)) {
                                        Text(
                                            "Stay safe while driving, pare!",
                                            fontFamily =  quicksandFontFamily,
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                    }

                                }
                            }
                            Divider(Modifier.padding(4.dp))
                            Text("In the last week...", fontFamily = quicksandFontFamily, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.padding(4.dp))
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(.5f),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Card (
                                    Modifier
                                        .weight(.5f)
                                        .fillMaxSize(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Box (
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(4.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ){
                                        Text(
                                            "PHP ${df.format(averageSalary)}",
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = quicksandFontFamily,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Box (
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.BottomStart
                                        ){
                                            Text("Average Earnings", fontSize = 14.sp, fontFamily = quicksandFontFamily,)
                                        }
                                    }
                                }
                                Spacer(Modifier.padding(4.dp))
                                Card (
                                    Modifier
                                        .weight(.5f)
                                        .fillMaxSize(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Box (
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(4.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ){
                                        Text(
                                            "PHP ${df.format(averageFuelCost)}",
                                            fontSize = 22.sp, fontWeight = FontWeight.Bold,
                                            fontFamily = quicksandFontFamily,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Box (
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.BottomStart
                                        ){
                                            Text("Average Expenses", fontSize = 14.sp, fontFamily = quicksandFontFamily,)
                                        }
                                    }
                                }
                            }
                        }
                    }


                    LazyColumn {
                        items(analytics ?: emptyList()) { item ->
                            AnalyticsCard(
                                date = item.date,
                                revenue = "Earnings: " + item.salary.toString(),
                                fuelCost = "Expenses: " + item.fuelCost.toString(),
                                distance = "Distance: " + formatDistanceToString(item.distance),
                                time = "Time: " + formatSecondsToTime(item.timer)
                            ) {
                            }
                        }
                    }
                }
            }
        }
    }
}

