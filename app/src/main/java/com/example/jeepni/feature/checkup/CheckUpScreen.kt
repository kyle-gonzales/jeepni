@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.jeepni.feature.checkup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jeepni.R
import com.example.jeepni.core.ui.*
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckUpScreen (
    viewModel: CheckUpViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onPopBackStack : () -> Unit,
) {
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

    JeepNiTheme {
        Surface {
            Scaffold(
                topBar = {
                    AppBar(
                        title = "JeepNi! Check-up",
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
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            item {
                                Button(
                                    onClick = {
                                        viewModel.onEvent(CheckUpEvent.OnOpenAddAlarmDialog(true))
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.outlineVariant,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    )
                                ){
                                    Icon(
                                        painter = painterResource(R.drawable.add_48px),
                                        contentDescription = null,
                                        modifier = Modifier.size(45.dp)
                                    )
                                }
                            }
                            itemsIndexed(viewModel.alarmList) {index, alarm ->
                                Button(
                                    onClick =  {
                                        viewModel.onEvent(CheckUpEvent.OnOpenEditAlarmDialog(true, index))
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.outlineVariant,
                                        contentColor = MaterialTheme.colorScheme.onSurface
                                    )
                                ){
                                    ComponentCard(alarm)
                                }
                            }
                        }
                    }
                })
            }
            if(viewModel.isAddComponentDialogOpen){
                AddDialog(
                    onDismiss = {viewModel.onEvent(
                        CheckUpEvent.OnDismissAdd
                    )},
                    pickedDate = viewModel.nextAlarm.toLocalDate(),
                    onDateChange = {viewModel.onEvent(
                        CheckUpEvent.OnNextAlarmChange(it)
                    )},
                    isRepeated = viewModel.isRepeated,
                    onRepeatabilityChange = {viewModel.onEvent(
                        CheckUpEvent.OnRepeatabilityChange(it)
                    )},
                    value = viewModel.intervalValue,
                    onValueChange = {viewModel.onEvent(
                        CheckUpEvent.OnIntervalValueChange(it)
                    )},
                    duration = viewModel.intervalType,
                    onDurationChange = {viewModel.onEvent(
                        CheckUpEvent.OnIntervalTypeChange(it)
                    )
                    },
                    onCancelClick = {viewModel.onEvent(
                        CheckUpEvent.OnDismissAdd
                    )
                    },
                    onSaveClick = {viewModel.onEvent(
                        CheckUpEvent.OnSaveAddClicked
                    )},
                    name = viewModel.alarmName,
                    onNameChange = {viewModel.onEvent(
                        CheckUpEvent.OnAlarmItemSelected(it)
                    )},
                    onNameChange1 = {viewModel.onEvent(
                        CheckUpEvent.OnCustomAlarmItemNameChanged(it)
                    )},
                    isError = viewModel.isError
                )
            }
            if (viewModel.isEditDeleteDialogOpen) {
                EditDeleteDialog(
                    alarmName = viewModel.alarmList[viewModel.alarmToEditIndex].name,
                    onDismiss = { viewModel.onEvent( CheckUpEvent.OnDismissEdit ) },
                    pickedDate = viewModel.nextAlarm.toLocalDate(),
                    onDateChange = {viewModel.onEvent(
                        CheckUpEvent.OnNextAlarmChange(it)
                    )},
                    isRepeated = viewModel.isRepeated,
                    onRepeatabilityChange = { viewModel.onEvent(
                        CheckUpEvent.OnRepeatabilityChange(it)
                    ) },
                    value = viewModel.intervalValue,
                    onValueChange = {viewModel.onEvent(
                        CheckUpEvent.OnIntervalValueChange(it)
                    )},
                    duration = viewModel.intervalType,
                    onDurationChange = {viewModel.onEvent(
                        CheckUpEvent.OnIntervalTypeChange(it)
                    )
                    },
                    onDeleteClick = {viewModel.onEvent(
                        CheckUpEvent.OnDeleteClicked
                    )},
                    onSaveClick = {viewModel.onEvent(
                        CheckUpEvent.OnSaveEditClicked
                    )},
                    isError = viewModel.isError
                )
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
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
                    JeepNiText(text = title)
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