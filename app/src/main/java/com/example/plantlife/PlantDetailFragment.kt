package com.example.plantlife

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.fragment_plant_detail.*
import java.io.File


class PlantDetailFragment : Fragment(R.layout.fragment_plant_detail) {
    private val util = Util()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundleData = arguments
        val plantName = bundleData?.getString("plantName")
        val plantScientificName = bundleData?.getString("plantScientificName")
        val plantHeight = bundleData?.getString("plantHeight")
        val plantWidth = bundleData?.getString("plantWidth")
        val plantPicture = bundleData?.getString("plantPicture")
        if (plantPicture != null) {
            val orientatedPicture = util.orientatePicture(File(plantPicture))
            detailImage.setImageBitmap(orientatedPicture)
        }

        if (plantName != "") {
            detailPlantName.text = plantName.toString()
        } else {
            detailPlantNameHeader.text = ""
        }
        if (plantScientificName != "") {
            detailScientificName.text = plantScientificName.toString()
        } else {
            detailScientificNameHeader.text = ""
        }
        if (plantWidth != "") {
            detailWidth.text = plantWidth.toString()
        } else {
            detailWidthHeader.text = ""
        }
        if (plantHeight != "") {
            detailHeight.text = plantHeight.toString()
        } else {
            detailHeightHeader.text = ""
        }
    }

}