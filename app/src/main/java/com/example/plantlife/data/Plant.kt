package com.example.plantlife.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_items")

data class Plant(
    @ColumnInfo(name = "plant_name")
    val name: String,
    @ColumnInfo(name = "plant_scientificName")
    val scientificName: String,
    @ColumnInfo(name = "plant_height")
    val height: String,
    @ColumnInfo(name = "plant_width")
    val width: String,
    @ColumnInfo(name = "plant_image")
    val picture: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}