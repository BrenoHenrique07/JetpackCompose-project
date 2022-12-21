package com.example.ap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.leanback.widget.Row
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
        var expanded by remember { mutableStateOf(false) } // initial value

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
                onValueChange = { type = it },
                label = {Text(text = "choose a type")},
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                VehicleType.values().forEach {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        VehicleType.values()
                    }) {
                        Text(text = VehicleType.values().toString())
                    }

                }
            }
            Spacer(modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = {expanded = !expanded}
                )
            )
        }

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Button(onClick = {
            //your onclick code here
        },elevation =  ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),  modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Submit")
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