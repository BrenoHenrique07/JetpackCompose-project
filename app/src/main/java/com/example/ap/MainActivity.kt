package com.example.ap

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.leanback.widget.Row
import androidx.leanback.widget.Visibility
import com.example.ap.data.DaoVehicleSingleton
import com.example.ap.model.Vehicle
import com.example.ap.model.VehicleType
import com.example.ap.ui.theme.APTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.text.Normalizer.Form

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Greeting(name: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        var model by remember { mutableStateOf("") }
        var type by remember { mutableStateOf("") }
        var price by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }


        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Model") },
            modifier = Modifier.align(Alignment.CenterHorizontally).
            fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )

        Box (
            modifier = Modifier.align(Alignment.CenterHorizontally)

        ){
            OutlinedTextField(
                value = type,
                onValueChange = {},
                label = {Text(text = "Choose a type...")},
                    modifier = Modifier.
                    fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        ,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },

            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                VehicleType.values().forEach {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        type = it.name
                    }) {
                        Text(text = it.type)
                    }

                }
            }
            Spacer(modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
            )
        }

        OutlinedTextField(
            value = price,
            onValueChange = { price = it},
            label = {Text(text = "Price") },
            modifier = Modifier.align(Alignment.CenterHorizontally).
            fillMaxWidth()
                .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = {
            if(model != "" && price != "" && type != "") {
                var v = Vehicle(model,price.toFloat(),VehicleType.valueOf(type))
                DaoVehicleSingleton.add(v)
                model = ""
                price = ""
                type = ""
            }
        },elevation =  ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),  modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 8.dp)
        ) {
            Text(text = "Submit")
        }
        VehicleList(vehicles = DaoVehicleSingleton.getVehicles()) {}
        AnimatedVisibility(
            visible = DaoVehicleSingleton.getVehicles().isEmpty(),
            enter = fadeIn(initialAlpha = 0f) + expandVertically(),
            exit = fadeOut(animationSpec = tween(durationMillis = 250)) + shrinkVertically()
        ) {
            Text(
                text = "There's no car registered yet :'(",
                fontSize = 25.sp,
                modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 30.dp)
                    .padding(vertical = 20.dp),
                )
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VehicleItemView(vehicle: Vehicle, onClick: () -> Unit) {
    var expandDetails by remember { mutableStateOf(false) }
    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = {
                expandDetails = !expandDetails
            },
            onLongClick = {
                vehicle.sold = true
                expandDetails = !expandDetails
            }
            ),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {

            Row(modifier =
            Modifier
            ) {
                Box(
                    modifier = Modifier
                        .background(if(vehicle.sold) Color.Red else Color.Green)
                        .size(8.dp, 60.dp)
                )
                Text(
                    text = vehicle.model,
                    textDecoration = if (vehicle.sold) { TextDecoration.LineThrough }
                    else{ TextDecoration.None },
                    fontSize = 20.sp,
                    modifier =
                    Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 16.dp)
                        .width(230.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Row (
                    modifier = Modifier.clipToBounds().align(Alignment.CenterVertically)
                        .weight(20.toFloat())
                        ){
                    AnimatedVisibility(
                        visible = vehicle.sold,
                        enter = fadeIn(initialAlpha = 0f) + expandVertically(),
                        exit = fadeOut(animationSpec = tween(durationMillis = 250)) + shrinkVertically(),
                        modifier = Modifier.wrapContentWidth()
                            .clipToBounds(),
                    ) {
                        Text(
                            text = "Sold",
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp,
                            modifier =
                            Modifier
                                .align(Alignment.CenterVertically)
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 20.dp),
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = expandDetails,
                enter = fadeIn(initialAlpha = 0f) + expandVertically(),
                exit = fadeOut(animationSpec = tween(durationMillis = 250)) + shrinkVertically()
            ) {
                Text(
                    text = stringResource(
                        id = R.string.description_text,
                        vehicle.price.toString(),
                        vehicle.type.type,
                        if (!vehicle.sold) "This vehicle is available"
                    else  "This vehicle is sold"
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun VehicleList(vehicles: List<Vehicle>, onClick: (vehicle: Vehicle) -> Unit) {
    LazyColumn {
        items(vehicles) { vehicle ->
            VehicleItemView(vehicle) {
                onClick(vehicle)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    APTheme {
        Greeting("Android")
    }
}