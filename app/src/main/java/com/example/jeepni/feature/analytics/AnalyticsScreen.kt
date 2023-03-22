package com.example.jeepni.feature.analytics

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.core.ui.AnalyticsCard
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun AnalyticsScreen(
//    date: String,
//    revenue: String,
//    expenses: String,
    viewModel : AnalyticsViewModel = hiltViewModel(),
    onNavigate : (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit,
) {

    val analytics = viewModel.analytics.collectAsState(initial = emptyList()) // this is the list that is used in the lazy column
    JeepNiTheme {
        Surface {
            AnalyticsCard(
                date = "2-22-2222",
                revenue = analytics.value[0].salary.toString(),
                expenses = analytics.value[0].fuelCost.toString()
            ) {
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLayout(){
//    AnalyticsScreen(
//        "date", "revenue", "expenses"
//    )
}