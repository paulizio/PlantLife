package com.example.plantlife.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.plantlife.*
import com.example.plantlife.data.Plant
import kotlinx.android.synthetic.main.plantitem.view.*
import java.io.File

class PlantAdapter(
    var plants: MutableList<Plant>
) :
    RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    private val util = Util()

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val activity = itemView.context as AppCompatActivity
        private val manager = activity.supportFragmentManager

        init {
            itemView.setOnClickListener { v: View ->
                val plantDetailFragment = PlantDetailFragment()
                val bundle = Bundle()
                val plant: Plant = plants[adapterPosition]
                bundle.putString("plantName", plant.name)
                bundle.putString("plantScientificName", plant.scientificName)
                bundle.putString("plantHeight", plant.height)
                bundle.putString("plantWidth", plant.width)
                bundle.putString("plantPicture", plant.picture)
                plantDetailFragment.arguments = bundle
                manager.beginTransaction().apply {
                    replace(R.id.fragmentHolder, plantDetailFragment)
                    addToBackStack(null)
                    commit()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.plantitem,
                parent,
                false
            )
        )
    }

    fun getPlantPosition(position: Int): Plant {
        return plants[position]
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val curPlant = plants[position]
        if (curPlant.picture != null) {
            val picture = File(curPlant.picture)
            val orientatedPicture = util.orientatePicture(picture)
            holder.itemView.apply {
                tvPlant.text = curPlant.name
                ivImage.setImageBitmap(orientatedPicture)
            }
        } else {
            holder.itemView.apply {
                tvPlant.text = curPlant.name
            }
        }
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    fun removeAt(adapterPosition: Int) {
        plants.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }
}