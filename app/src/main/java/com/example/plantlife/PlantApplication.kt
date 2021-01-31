package com.example.plantlife

import android.app.Application
import com.example.plantlife.data.PlantDatabase
import com.example.plantlife.data.PlantRepository
import com.example.plantlife.viewmodels.PlantViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class PlantApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@PlantApplication))
        bind() from singleton { PlantDatabase(instance()) }
        bind() from singleton { PlantRepository(instance()) }
        bind() from provider {
            PlantViewModelFactory(instance())
        }
    }
}