package com.example.plantlife.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Plant)

    @Delete
    suspend fun delete(item: Plant)

    @Query("SELECT * FROM plant_items")
    fun getAllPlants(): LiveData<List<Plant>>

}