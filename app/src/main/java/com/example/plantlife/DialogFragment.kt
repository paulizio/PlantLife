package com.example.plantlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.plantlife.adapters.PlantAdapter
import com.example.plantlife.data.Plant
import com.example.plantlife.viewmodels.PlantViewModel
import com.example.plantlife.viewmodels.PlantViewModelFactory
import kotlinx.android.synthetic.main.fragment_dialog.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.io.File


private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42

class DialogFragment() : Fragment(R.layout.fragment_dialog), KodeinAware {
    override val kodein: Kodein by kodein()
    private var photoFile: File? = null
    private val util = Util()
    private val factory: PlantViewModelFactory by instance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this, factory).get(PlantViewModel::class.java)
        val adapter = PlantAdapter(mutableListOf())
        viewModel.getAllPlantItems().observe(viewLifecycleOwner, Observer {
            adapter.plants = it as MutableList<Plant>
            adapter.notifyDataSetChanged()
        })
        takePhotoButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)
            val fileProvider = FileProvider.getUriForFile(
                this.requireContext(),
                "com.example.plantlife.fileprovider",
                photoFile!!
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(
                    activity,
                    "Unable to open camera",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        submitButton.setOnClickListener {
            val name = plantNameText.text.toString()
            val scientificName = plantScientificName.text.toString()
            val height = plantHeight.text.toString()
            val width = plantWidth.text.toString()
            val picture = photoFile?.absolutePath
            if (picture != null) {
                viewModel.upsert(Plant(name, scientificName, height, width, picture))
            } else {
                viewModel.upsert(Plant(name, scientificName, height, width, null))
            }
            adapter.notifyItemInserted(adapter.plants.size - 1)
            navigateToHomeFragment()
        }

        cancelButton.setOnClickListener {
            navigateToHomeFragment()
        }

    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    private fun navigateToHomeFragment() {
        val homeFragment = HomeFragment()
        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.fragmentHolder, homeFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenImage = photoFile?.let { util.orientatePicture(it) }
            imageViewDialog.setImageBitmap(takenImage)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}