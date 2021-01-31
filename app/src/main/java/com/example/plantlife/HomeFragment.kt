package com.example.plantlife

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantlife.adapters.PlantAdapter
import com.example.plantlife.data.Plant
import com.example.plantlife.viewmodels.PlantViewModel
import com.example.plantlife.viewmodels.PlantViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment() : Fragment(R.layout.fragment_home), KodeinAware {
    override val kodein: Kodein by kodein()
    private val factory: PlantViewModelFactory by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = ViewModelProviders.of(this, factory).get(PlantViewModel::class.java)
        val adapter = PlantAdapter(mutableListOf())
        rvPlants.layoutManager = LinearLayoutManager(this.context)
        rvPlants.isNestedScrollingEnabled = false
        rvPlants.adapter = adapter
        rvPlants.setItemViewCacheSize(20)
        viewModel.getAllPlantItems().observe(viewLifecycleOwner, Observer {
            adapter.plants = it as MutableList<Plant>
            adapter.notifyDataSetChanged()
        })
        val swipeHandler = object : SwipeToDeleteCallBack(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rvPlants.adapter as PlantAdapter
                viewModel.delete(adapter.getPlantPosition(viewHolder.adapterPosition))
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvPlants)

        floatingActionButton.setOnClickListener {
            openDialogFragment()
        }
    }

    private fun openDialogFragment() {
        val dialogFragment = DialogFragment()
        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.fragmentHolder, dialogFragment)
            commit()
        }
    }
}
