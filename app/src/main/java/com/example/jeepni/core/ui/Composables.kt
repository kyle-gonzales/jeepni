package com.example.jeepni.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.jeepni.R
import com.example.jeepni.core.ui.theme.JeepNiTheme
import com.example.jeepni.core.ui.theme.quicksandFontFamily
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
    bgColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    width: Float = 1f,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth(width)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(36),
        colors = ButtonDefaults.buttonColors(bgColor, contentColor)
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
    readOnly: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, fontFamily = quicksandFontFamily) },
            isError = isError,
            shape = RoundedCornerShape(36),
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
        )
    }
}

@Composable
fun BackIconButton(
    onClick: () -> Unit
) {
    IconButton(onClick = { onClick() })
    {
        Icon(Icons.Filled.ArrowBack, contentDescription = null)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun T() {

    var text by remember {
        mutableStateOf("")
    }
    var width = 10.dp

    var isValid by remember {
        mutableStateOf(false)
    }

    var icon = null
    JeepNiTheme {
        Surface(
            modifier = Modifier
                .padding(16.dp) //remove this after
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = text,
                    onValueChange = { text = it },
                    label = { Text(text = "label", fontFamily = quicksandFontFamily) },
                    isError = isValid,
                    leadingIcon = null,
                    shape = RoundedCornerShape(36),
                    textStyle = TextStyle(fontFamily = quicksandFontFamily),
                    supportingText = {
                        if (isValid) {
                            Text(
                                text = "Invalid Phone Number", //! convert to state
                                color = MaterialTheme.colorScheme.error,
                                fontFamily = quicksandFontFamily,
                            )
                        }
                    }
                )
            }
        }
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
    onChange: (LocalDate) -> Unit
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
    ) {
        Box(
            modifier = Modifier.padding(12.dp)
        ) {
            OutlinedButton(
                onClick = { dateDialogState.show() },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = formattedDate,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = quicksandFontFamily
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Text(
                text = label,
                fontFamily = quicksandFontFamily,
                modifier = Modifier
                    .offset(x = 30.dp, y = -15.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok", textStyle = TextStyle(color = Color(0xFF006E14))) {
                onChange(selectedDate)
            }
            negativeButton(text = "Cancel", textStyle = TextStyle(color = Color(0xFF006E14)))
        },
    ) {
        datepicker(
            initialDate = selectedDate,
            title = "Pick a date",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = Color(0xFF006E14),
                headerTextColor = Color.White,
                calendarHeaderTextColor = Color.Black,
                dateActiveBackgroundColor = Color(0xFF006E14),
                dateActiveTextColor = Color.Black,

            ),
            allowedDateValidator = {
                it.isBefore(LocalDate.now()) || it.isEqual(LocalDate.now()) // disable future dates
            }
        ) {
            selectedDate = it
        }
    }
}

@Composable
fun JeepPartCheckBox(
    jeepPart: String,
    onCheckedChange: (Boolean) -> Unit,
    isChecked: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
        )
        Text(
            text = jeepPart,
        )
    }
}

//@Composable
//fun FilterIconButton(
//    onClick : () -> Unit,
//    menuItems: List<String>,
//    onMenuItemClick: (String) -> Unit
//){
//    var expanded by remember { mutableStateOf(false) }
//
//    IconButton(onClick = { expanded = true })
//    {
//        Icon(Icons.Filled.Menu, contentDescription = "Menu")
//    }

//    DropdownMenu(
//        expanded = expanded,
//        onDismissRequest = { expanded = false })
//    {
//        menuItems.forEach{ item ->
//            DropdownMenuItem(
//               onClick = {
//                   onMenuItemClick(item)
//                   expanded = false
//               }){
//                Text(item)
//            }
//        }
//    }
//}

@Composable
fun PartsList(
    parts: List<String>,
    isCheckedLeft: List<Boolean>,
    isCheckedRight: List<Boolean>,
    onCheckboxChangeLeft: (List<Boolean>) -> Unit,
    onCheckboxChangeRight: (List<Boolean>) -> Unit
) {

    val newListLeft = isCheckedLeft.toMutableList()
    val newListRight = isCheckedRight.toMutableList()

    Row {
        Column(modifier = Modifier.weight(1f)) {
            parts.subList(0, parts.size / 2).forEachIndexed { index, part ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = isCheckedLeft[index],
                        onCheckedChange = {
                            newListLeft[index] = it
                            onCheckboxChangeLeft(newListLeft)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = part
                    )
                }
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            parts.subList(parts.size / 2, parts.size).forEachIndexed { index, part ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = isCheckedRight[index],
                        onCheckedChange = {
                            newListRight[index] = it
                            onCheckboxChangeRight(newListRight)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = part
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    titledesc: String,
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
                    JeepNiText(text = titledesc,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp)
                }
            },
            navigationIcon = {
                BackIconButton(onClick = onPopBackStack)
            }
        )
    }
}