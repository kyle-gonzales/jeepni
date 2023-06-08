package com.example.jeepni.core.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import com.example.jeepni.R
import com.example.jeepni.core.ui.theme.quicksandFontFamily
import com.example.jeepni.util.Constants
import com.example.jeepni.util.Constants.COMPONENTS
import java.time.LocalDate

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDeleteDialog(
    alarmName: String,
    onDismiss: () -> Unit,
    pickedDate: LocalDate,
    onDateChange: (LocalDate)->Unit,
    isRepeated: Boolean,
    onRepeatabilityChange: (Boolean)->Unit,
    value: String,
    onValueChange: (String)->Unit,
    duration: String,
    onDurationChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
    isError: Boolean,
) {

    val repeatedTextState by remember{ //why is this not working?
        derivedStateOf {
            if(isRepeated)
                "Repeat (On)"
            else
                "Repeat (Off)"
        }
    }
    Dialog(onDismissRequest = onDismiss) {
        Card(
            elevation = CardDefaults.cardElevation (
                defaultElevation =  4.dp
            ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = alarmName,
                        fontSize = 20.sp,
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(20.dp))
                DatePicker(
                    label = "Date of next alarm",
                    pickedDate = pickedDate,
                    onChange = onDateChange,
                    dateValidator = {it.isAfter(LocalDate.now())},
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.height(20.dp),
                            painter = painterResource(R.drawable.hourglass_top),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(text = repeatedTextState, fontFamily = quicksandFontFamily)
                    }
                    Switch(
                        //change style
                        modifier = Modifier.height(17.dp),
                        checked = isRepeated,
                        onCheckedChange = { onRepeatabilityChange(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.background,
                            checkedBorderColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = Color.LightGray,
                            uncheckedTrackColor = MaterialTheme.colorScheme.background,
                            uncheckedBorderColor = Color.LightGray,
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedTextField(
                        enabled = isRepeated,
                        modifier = Modifier
                            .width(60.dp)
                            .height(50.dp),
                        value = value,
                        isError = isError,
                        textStyle = TextStyle(fontFamily = quicksandFontFamily),
                        singleLine = true,
                        onValueChange = onValueChange,
                        supportingText = {
                            if (isError) {
                                Text(
                                    text = "Input should be within 1-100", //! convert to state
                                    color = MaterialTheme.colorScheme.error,
                                    fontFamily = quicksandFontFamily,
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(Modifier.width(5.dp))
                    CustomRadioButton(isEnabled = isRepeated, options = listOf("year", "month", "day"), selectedOption = duration, onOptionSelected = onDurationChange)
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SolidButton(
                        onClick = onDeleteClick,
                        bgColor = MaterialTheme.colorScheme.error,
                        width = 0.5f
                    ){
                        Text(text = "Delete", fontFamily = quicksandFontFamily)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    SolidButton(
                        onClick = onSaveClick
                    ) {
                        Text(text = "Save", fontFamily = quicksandFontFamily)
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDialog(
    onDismiss: () -> Unit,
    pickedDate: LocalDate,
    onDateChange: (LocalDate)->Unit,
    isRepeated: Boolean,
    onRepeatabilityChange: (Boolean)->Unit,
    value: String,
    onValueChange: (String)->Unit,
    duration: String,
    onDurationChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    onNameChange1: (String) -> Unit,
    isError: Boolean
) {
    val state by remember{
        derivedStateOf {
            if(isRepeated){"On"}
            else{"Off"}
        }
    }
    Dialog(onDismissRequest = onDismiss) {
        Card (
            elevation = CardDefaults.cardElevation(
                defaultElevation =  4.dp
            ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Add Alarm",
                        fontSize = 20.sp,
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(12.dp))

                AlarmNameGrid(
                    name,
                    { onNameChange(it) },
                    { onNameChange1(it) },
                )

                Spacer(Modifier.height(20.dp))
                DatePicker(
                    label = "Date of next alarm",
                    pickedDate = pickedDate,
                    onChange = onDateChange,
                    dateValidator = {it.isAfter(LocalDate.now())}
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.height(20.dp),
                            painter = painterResource(R.drawable.hourglass_top),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(text = "Repeat (" + state + ")", fontFamily = quicksandFontFamily)
                    }
                    Switch(
                        //change style
                        modifier = Modifier.height(17.dp),
                        checked = isRepeated,
                        onCheckedChange = onRepeatabilityChange,
                        enabled = true,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.background,
                            checkedBorderColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = Color.LightGray,
                            uncheckedTrackColor = MaterialTheme.colorScheme.background,
                            uncheckedBorderColor = Color.LightGray,
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedTextField(
                        enabled = isRepeated,
                        modifier = Modifier
                            .width(60.dp)
                            .height(50.dp),
                        value = value,
                        isError = isError,
                        textStyle = TextStyle(fontFamily = quicksandFontFamily),
                        singleLine = true,
                        onValueChange = onValueChange,
                        supportingText = {
                            if (isError) {
                                Text(
                                    text = "Input should be within 1-100", //! convert to state
                                    color = MaterialTheme.colorScheme.error,
                                    fontFamily = quicksandFontFamily,
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(Modifier.width(5.dp))
                    CustomRadioButton(isEnabled = isRepeated, options = listOf("year", "month", "day"), selectedOption = duration, onOptionSelected = onDurationChange)
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SolidButton(
                        onClick = onCancelClick,
                        bgColor = MaterialTheme.colorScheme.error,
                        width = 0.5f
                    ){
                        Text(text = "Cancel", fontFamily = quicksandFontFamily)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    SolidButton(
                        onClick = onSaveClick
                    ) {
                        Text(text = "Save", fontFamily = quicksandFontFamily)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmNameGrid(
    selectedAlarm: String,
    onAlarmItemChanged: (String) -> Unit,
    onCustomAlarmNameChanged: (String) -> Unit,
){
    var isCustomAlarmItemEnabled by remember {
        mutableStateOf(false)
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(4.dp)
    ){
        items(COMPONENTS){
            val isSelected = (selectedAlarm == it && selectedAlarm in COMPONENTS) && !isCustomAlarmItemEnabled
            OutlinedButton(
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {},
                    )
                    .fillMaxWidth()
                    .height(60.dp),
                onClick = {
                    onAlarmItemChanged(it)
                    isCustomAlarmItemEnabled = false
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background ,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                contentPadding = PaddingValues(3.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        painter = painterResource(Constants.ICON_MAP.getValue(it)),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = it,
                        fontFamily = quicksandFontFamily,
                        maxLines = 1,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        item {//custom alarm item
            OutlinedButton(
                modifier = Modifier
                    .selectable(
                        selected = isCustomAlarmItemEnabled,
                        onClick = {}
                    )
                    .fillMaxWidth()
                    .height(60.dp),
                onClick = {
                    isCustomAlarmItemEnabled = true
                    onAlarmItemChanged("")
                },
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCustomAlarmItemEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background ,
                    contentColor = if (isCustomAlarmItemEnabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                contentPadding = PaddingValues(3.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Add Custom",
                        fontFamily = quicksandFontFamily,
                        maxLines = 2,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        item(span = { GridItemSpan(2) }){
            if(isCustomAlarmItemEnabled){
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    value = selectedAlarm,
                    onValueChange = { onCustomAlarmNameChanged(it) },
                    label = {
                        Text(
                            text = "Custom Alarm",
                            fontFamily = quicksandFontFamily,
                            textAlign = TextAlign.Start,
                            fontSize = 12.sp,
                        )
                    },
                    shape = RoundedCornerShape(20),
                    textStyle = TextStyle(
                        fontFamily = quicksandFontFamily,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}
@Composable
fun DurationButton(
    isEnabled: Boolean,
    text:String,
    selectedOption:String,
    onOptionSelected:(String)->Unit
){
    var width = 0.33f
    if(text == "month"){
        width = 0.55f
    }
    else if (text == "day") { width = 1f }
    if(text == selectedOption){
        OutlinedButton(
            enabled = isEnabled,
            modifier = Modifier
                .height(50.dp)
                .selectable(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                    }
                )
                .fillMaxWidth(width)
                .padding(horizontal = 0.dp),
            onClick = {onOptionSelected(text)},
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.DarkGray
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface)
        ){
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontFamily = quicksandFontFamily,
                modifier = Modifier.padding(start = 0.dp),
                fontSize = (10).sp
            )
        }
    }
    else {
        OutlinedButton(
            enabled = isEnabled,
            modifier = Modifier
                .height(50.dp)
                .selectable(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                    }
                )
                .fillMaxWidth(width)
                .padding(horizontal = 0.dp),
            onClick = { onOptionSelected(text) },
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.DarkGray
            ),
            contentPadding = PaddingValues(horizontal = 1.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontFamily = quicksandFontFamily,
                fontSize = (10).sp
            )
        }
    }
}

@Composable
fun CustomRadioButton(
    isEnabled: Boolean,
    options:List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        options.forEach { text ->
            DurationButton(isEnabled, text, selectedOption, onOptionSelected)
        }
    }
}

@Composable
fun AlarmNameDropDown(
    label: String,
    expanded: Boolean,
    value: String,
    onClickIcon: (Boolean) -> Unit,
    onSizeChange: (Size) -> Unit,
    onSelected: (Int) -> Unit,
    size: Size,
    items: List<String>
) {
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            JeepNiTextField(
                readOnly = true,
                value = value,
                onValueChange = {},
                label = label,
                trailingIcon = {
                    Icon(
                        icon,
                        contentDescription = null,
                        Modifier.clickable {
                            onClickIcon(!expanded)
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        onSizeChange(it.size.toSize())
                    }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onClickIcon(false) },
                modifier = Modifier
                    .width(
                        with(LocalDensity.current) {
                            size.width.toDp()
                        }
                    )
                    .requiredHeight(230.dp)
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        modifier = Modifier.height(50.dp),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = Constants.ICON_MAP[s]!!),
                                contentDescription = null,
                                modifier = Modifier.height(20.dp)
                            )
                                      },
                        text = { Text(text = s) },
                        onClick = {
                            onSelected(index)
                            onClickIcon(false)
                        }
                    )
                    Divider(Modifier.padding(4.dp, 0.dp))
                }
            }
        }
    }
}