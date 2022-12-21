package com.example.ap.data

import com.example.ap.model.Vehicle

object DaoVehicleSingleton {
    private var serial: Long = 1
    private val vehicles = ArrayList<Vehicle>()

    fun add(v: Vehicle) {
        v.id = serial++
        vehicles.add(0, v)
    }
    fun getVehicles(): ArrayList<Vehicle> {
        return vehicles
    }
    fun getVehicleById(id: Long): Vehicle? {
        for(v in vehicles) {
            if(v.id == id)
                return v
        }
        return null
    }
}
