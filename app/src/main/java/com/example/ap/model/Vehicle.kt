package com.example.ap.model

data class Vehicle (
    var id: Long = 0,
    val model: String,
    val price: Float,
    val type: VehicleType,
    val sold: Boolean = false,
) { constructor(model: String, price: Float, type: VehicleType): this(0,"",0.0.toFloat(),VehicleType.HATCH ) {
    this.id = id
}

}
