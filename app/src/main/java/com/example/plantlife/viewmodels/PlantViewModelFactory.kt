package com.example.plantlife.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantlife.data.PlantRepository

class PlantViewModelFactory(
    private val repository: PlantRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlantViewModel(repository) as T
    }
}