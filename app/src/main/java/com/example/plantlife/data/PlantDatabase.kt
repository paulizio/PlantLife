package com.example.plantlife.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Plant::class],
    version = 1
)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun getPlantDao(): PlantDao

    companion object {
        @Volatile
        private var instance: PlantDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDatabase(
                        context
                    ).also { instance = it }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PlantDatabase::class.java, "PlantDB.db"
            ).build()
    }
}