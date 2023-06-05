package com.example.jeepni.core.ui

import android.widget.Button
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.jeepni.R
import com.example.jeepni.core.data.model.AlarmContent
import com.example.jeepni.core.ui.theme.Black
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.White
import com.example.jeepni.core.ui.theme.quicksandFontFamily
import com.example.jeepni.util.Constants.ICON_MAP
import com.example.jeepni.util.PermissionTextProvider
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Gradient(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        content()
    }
}

@Composable
fun Container(
    // provides structure for login, signup, terms & conditions
    height: Float,
    content: @Composable () -> Unit
) {
    Gradient {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(height),
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    Arrangement.SpaceEvenly,
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun Logo(
    width: Dp = 100.dp,
    height: Dp = 100.dp
) {
    Image(
        painter = painterResource(id = R.drawable.samplelogo),
        contentDescription = "JeepNi Logo",
        modifier = Modifier
            .width(width)
            .height(height)
    )
}

@Composable
fun JeepNiText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    fontSize: TextUnit = 14.sp,
    fontStyle: FontStyle = FontStyle.Normal,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Medium
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        textAlign = textAlign,
        fontWeight = fontWeight,
        fontFamily = quicksandFontFamily,
    )
}

@Composable
fun SolidButton(
    isEnabled: Boolean = true,
    bgColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    bgColorDisabled: Color = Color.LightGray,
    contentColorDisabled: Color = Color.DarkGray,
    width: Float = 1f,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        enabled = isEnabled,
        onClick = onClick,
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth(width)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.buttonColors(
            bgColor, contentColor, bgColorDisabled, contentColorDisabled
        )
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JeepNiTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    textAlign:TextAlign = TextAlign.Start
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, fontFamily = quicksandFontFamily, textAlign = textAlign) },
            isError = isError,
            shape = RoundedCornerShape(20),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = TextStyle(fontFamily = quicksandFontFamily),
            singleLine = singleLine,
            readOnly = readOnly,
            supportingText = {
                if (isError) {
                    Text(
                        text = errorMessage, //! convert to state
                        color = MaterialTheme.colorScheme.error,
                        fontFamily = quicksandFontFamily,
                    )
                }
            },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.background)
        )
    }
}

@Composable
fun BackIconButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier.offset(-12.dp)
    )
    {
        Icon(Icons.Filled.ArrowBack, contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDown(
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
                    .height(65.dp)
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
                    .requiredHeight(270.dp)
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = { Text(
                            text = s,
                            fontFamily = quicksandFontFamily,
                            fontSize = 16.sp
                        ) },
                        onClick = {
                            onSelected(index)
                            onClickIcon(false)
                        },
                        contentPadding = PaddingValues(start = 15.dp)
                    )
                    Divider(Modifier.padding(4.dp, 0.dp))
                }
            }
        }
    }
}

@Composable
fun AnalyticsCard(
    date: String,
    revenue: String,
    expenses: String,
    content: @Composable () -> Unit
) {
    Row {
        Card(
            shape = RoundedCornerShape(size = 5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = date,
                    fontSize = 15.sp,
                    fontFamily = quicksandFontFamily,
                    fontWeight = FontWeight.Bold
                )
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp)
                ) {
                    Text(
                        text = revenue,
                        fontSize = 15.sp,
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = expenses,
                        fontSize = 15.sp,
                        fontFamily = quicksandFontFamily,
                        fontWeight = FontWeight.Light
                    )
                }
                content()
            }
        }
    }
}

@Composable
fun JeepNiAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String = "Confirm",
    dismissText: String = "Cancel",
    titleText: String = "Alert",
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface {
        AlertDialog(
            onDismissRequest = onDismiss,
            modifier = modifier,
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                    onConfirm()
                }
                ) {
                    Text(confirmText, fontFamily = quicksandFontFamily)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss,
                ) {
                    Text(dismissText, fontFamily = quicksandFontFamily)
                }
            },
            title = {
                Text(titleText, fontFamily = quicksandFontFamily)
            },
            icon = icon,
            text = content,
        )

    }

}

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onGoToAppSettings: () -> Unit, // action when permissions are "permanently declined" (user declines permissions more than two times)
) {

    Surface {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                    if (isPermanentlyDeclined) {
                        onGoToAppSettings()
                    } else {
                        onConfirm()
                    }
                }
                ) {
                    Text(
                        text = if (isPermanentlyDeclined) {
                            "Grant Permissions"
                        } else {
                            "OK"
                        }, fontFamily = quicksandFontFamily
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss,
                ) {
                    Text("Cancel", fontFamily = quicksandFontFamily)
                }
            },
            title = {
                Text("Permission Required", fontFamily = quicksandFontFamily)
            },
            text = {
                Text(
                    text = permissionTextProvider.getDescription(isPermanentlyDeclined)
                )
            },
        )

    }
}

@Composable
fun DatePicker(
    label: String,
    pickedDate: LocalDate,
    onChange: (LocalDate) -> Unit,
    dateValidator: (LocalDate) -> Boolean
) {
    var selectedDate by remember {
        mutableStateOf(
            pickedDate
        )
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MM/dd/yyyy")
                .format(selectedDate)
        }
    }
    val dateDialogState = rememberMaterialDialogState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box() {
            OutlinedButton(
                onClick = { dateDialogState.show() },
                modifier = Modifier.height(60.dp),
                shape = RoundedCornerShape(20),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                        text = formattedDate,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = quicksandFontFamily
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar),
                        modifier = Modifier.size(24.dp),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Text(
                text = label,
                fontSize = 11.sp,
                fontFamily = quicksandFontFamily,
                modifier = Modifier
                    .offset(x = 15.dp, y = -15.dp)
                    .padding(10.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(
                text = "Save",
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = quicksandFontFamily,
                    fontWeight = FontWeight.Bold
                )
            ) {
                onChange(selectedDate)
            }
            negativeButton(
                text = "Cancel",
                textStyle = TextStyle(
                    color = Black,
                    fontFamily = quicksandFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    ) {
        datepicker(
            initialDate = selectedDate,
            title = "Pick a date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.primary,
                headerTextColor = MaterialTheme.colorScheme.onPrimary,
                calendarHeaderTextColor = MaterialTheme.colorScheme.onPrimary,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.primary,
                dateActiveTextColor = MaterialTheme.colorScheme.onPrimary,
                dateInactiveBackgroundColor = White,
                dateInactiveTextColor = Black
            ),
            allowedDateValidator = dateValidator
        ) {
            selectedDate = it
        }
    }
}

@Composable
fun ComponentCard(
    alarm:AlarmContent
) {
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MM/dd/yyyy")
                .format(alarm.nextAlarmDate)
        }
    }
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(modifier = Modifier.padding(8.dp),) {
            Icon(
                painter = painterResource(ICON_MAP[alarm.name]!!),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Column(modifier = Modifier.padding(8.dp)) {
            JeepNiText(
                text = alarm.name
            )
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Icon(
                    painter = painterResource(R.drawable.hourglass_top),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                JeepNiText(
                    text = formattedDate,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            val repeatability by remember {
                derivedStateOf {
                    if (alarm.isRepeatable) {
                        alarm.interval.first.toString() + " " + alarm.interval.second
                    } else {
                        "Off"
                    }
                }
            }
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Icon(
                    painter = painterResource(R.drawable.alarm),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                JeepNiText(
                    text = repeatability,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}