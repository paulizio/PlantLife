package com.example.plantlife.data

import com.example.plantlife.data.Plant
import com.example.plantlife.data.PlantDatabase

class PlantRepository(
    private val db: PlantDatabase
) {
    suspend fun upsert(item: Plant) = db.getPlantDao().upsert(item)

    suspend fun delete(item: Plant) = db.getPlantDao().delete(item)

    fun getAllPlantItems() = db.getPlantDao().getAllPlants()
}