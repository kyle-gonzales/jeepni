package com.example.jeepni.feature.analytics

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.jeepni.core.ui.AnalyticsCard
import com.example.jeepni.core.ui.theme.JeepNiTheme

@Composable
fun AnalyticsScreen(
    date: String,
    revenue: String,
    expenses: String,
) {
    JeepNiTheme {
        Surface {
            AnalyticsCard(
                date = date,
                revenue = revenue,
                expenses = expenses
            ) {
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLayout(){
    AnalyticsScreen(
        "date", "revenue", "expenses"
    )
}