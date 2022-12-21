package com.example.ap.model

data class Vehicle (
    var id: Long = 0,
    val model: String,
    val price: Float,
    val type: VehicleType,
    val sold: Boolean = false,
) {
}
