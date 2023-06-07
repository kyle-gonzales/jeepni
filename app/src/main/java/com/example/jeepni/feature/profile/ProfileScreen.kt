package com.example.jeepni.feature.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.core.ui.JeepNiBasicAppBar
import com.example.jeepni.core.ui.JeepNiText
import com.example.jeepni.core.ui.SolidButton
import com.example.jeepni.core.ui.theme.dark_primaryContainer
import com.example.jeepni.core.ui.theme.quicksandFontFamily
import com.example.jeepni.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen (
    viewModel: ProfileViewModel = hiltViewModel(),
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

    Surface {
        Scaffold(
            topBar = {
                 JeepNiBasicAppBar(
                     title = "Profile Screen",
                    onPopBackStack = onPopBackStack
                 )
            },
            content = {
                ProfileContent(
                    name = viewModel.name,
                    email = viewModel.email,
                    onLogOutClicked = {
                        viewModel.onEvent(ProfileEvent.OnLogOutClicked)
                    },
                    paddingValues = it
                )
            }
        )
    }

}

@Composable
private fun ProfileContent(
    name : String? = null,
    email : String?,
    onLogOutClicked : () -> Unit,
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .background(dark_primaryContainer),
            )
            Spacer(modifier = Modifier.height(24.dp))

            JeepNiText(text = "NAME")
            JeepNiText(
                text = if (name.isNullOrEmpty()) "No name set..." else name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (name.isNullOrEmpty()) Color.DarkGray else MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(8.dp))
            JeepNiText(text = "EMAIL")
            JeepNiText(
                text = email ?: "No email set...",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        SolidButton(
            onClick = { onLogOutClicked() },
            bgColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.error,
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.error)
        ) {
            Text(text = "Log Out", fontFamily = quicksandFontFamily, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}