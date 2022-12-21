package com.example.ap

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.leanback.widget.Row
import com.example.ap.data.DaoVehicleSingleton
import com.example.ap.model.Vehicle
import com.example.ap.model.VehicleType
import com.example.ap.ui.theme.APTheme
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
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Box (
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            OutlinedTextField(
                value = type,
                onValueChange = { },
                label = {Text(text = "Choose a type...")},
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                VehicleType.values().forEach {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        type = it.toString()
                    }) {
                        Text(text = it.name)
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
            modifier = Modifier.align(Alignment.CenterHorizontally),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = {
            if(model != "" && price != "" && type != "") {
                var v = Vehicle(model,price.toFloat(),VehicleType.valueOf(type))
                DaoVehicleSingleton.add(v)
                Log.i("teste", v.toString())
                model = ""
                price = ""
                type = ""
            }
        },elevation =  ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),  modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Submit")
        }
        VehicleList(vehicles = DaoVehicleSingleton.getVehicles()) {}
    }
}
@Composable
fun VehicleItemView(vehicle: Vehicle, onClick: () -> Unit) {
    Card(
        modifier =
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        elevation = 2.dp
    ) {
        Row(modifier =
        Modifier
            .padding(8.dp)
        ) {
            Text(
                text = vehicle.model,
                fontSize = 20.sp,
                modifier =
                Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 8.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
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