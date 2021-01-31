package com.example.plantlife.viewmodels

import androidx.lifecycle.ViewModel
import com.example.plantlife.data.PlantRepository
import com.example.plantlife.data.Plant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantViewModel(
    private val repository: PlantRepository
) : ViewModel() {
    fun upsert(item: Plant) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }

    fun delete(item: Plant) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllPlantItems() = repository.getAllPlantItems()
}