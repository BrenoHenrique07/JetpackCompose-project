package com.example.ap.model

data class Vehicle (
    var id: Long = 0,
    var model: String,
    var price: Float,
    var type: VehicleType,
    val sold: Boolean = false,
) { constructor(model: String, price: Float, type: VehicleType): this(0,"",0.0.toFloat(),VehicleType.HATCH ) {
    this.id = id
    this.model = model
    this.price = price
    this.type = type
}

}
