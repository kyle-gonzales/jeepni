package com.example.jeepni.core.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import com.example.jeepni.R
import com.example.jeepni.core.ui.theme.quicksandFontFamily
import com.example.jeepni.util.Alarm
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
    onDateChange:(LocalDate)->Unit,
    isRepeated:Boolean,
    onRepeatabilityChange: (Boolean)->Unit,
    value:String,
    onValueChange: (String)->Unit,
    duration:String,
    onDurationChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
    isError:Boolean,
) {
    val state by remember{
        derivedStateOf {
            if(isRepeated){"On"}
            else{"Off"}
        }
    }
    Dialog(onDismissRequest = onDismiss) {
        Card(
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
    onDateChange:(LocalDate)->Unit,
    isRepeated:Boolean,
    onRepeatabilityChange: (Boolean)->Unit,
    value:String,
    onValueChange: (String)->Unit,
    duration:String,
    onDurationChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    isNameDropdownClicked: Boolean,
    name:String,
    nameDropdownSize: Size,
    onNameSizeChange: (Size) -> Unit,
    onNameDropDownClicked: (Boolean) -> Unit,
    onNameChange: (Int)-> Unit,
    isError:Boolean
) {
    val state by remember{
        derivedStateOf {
            if(isRepeated){"On"}
            else{"Off"}
        }
    }
    Dialog(onDismissRequest = onDismiss) {
        Card(
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
                Spacer(Modifier.height(20.dp))
                AlarmNameDropDown(
                    label = "Alarm name",
                    expanded = isNameDropdownClicked,
                    value = name,
                    onClickIcon = onNameDropDownClicked,
                    onSizeChange = onNameSizeChange,
                    onSelected = onNameChange,
                    size = nameDropdownSize,
                    items = COMPONENTS
                )
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
                modifier = Modifier.padding(start = 2.dp),
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
                        leadingIcon = { Icon(painter = painterResource(id = Constants.ICON_MAP[s]!!), contentDescription = null)},
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