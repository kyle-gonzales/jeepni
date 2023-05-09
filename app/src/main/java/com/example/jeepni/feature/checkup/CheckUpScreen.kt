package com.example.jeepni.feature.checkup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
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
                else -> {}
            }
        }
    }

    JeepNiTheme() {
        Surface {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    JeepNiText("CheckUp Screen")
                    //try, will change this to lazygrid
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                        Column() {
                            ComponentCard("LTFRB Inspection", "mm/dd/yyyy", "3 months", icon = painterResource(
                                R.drawable.tire_repair)
                            )
                        }
                        Column() {
                            ComponentCard("LTFRB Inspection", "mm/dd/yyyy", "3 months", icon = painterResource(
                                R.drawable.tire_repair)
                            )
                        }
                    }
                }

            }

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun Preview(){
    CheckUpScreen(onNavigate = {}) {
        
    }
}