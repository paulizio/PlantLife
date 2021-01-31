package com.example.plantlife

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Log
import java.io.File

class Util {
    fun orientatePicture(photoFile: File): Bitmap? {
        try {
            val exif = ExifInterface(photoFile.absolutePath)
            val orientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)
            Log.d("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            when (orientation) {
                6 -> matrix.postRotate(90F)
                3 -> matrix.postRotate(180F)
                8 -> matrix.postRotate(270F)
            }
            var takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            takenImage = Bitmap.createBitmap(
                takenImage,
                0,
                0,
                takenImage.width,
                takenImage.height,
                matrix,
                true
            ) // rotating bitmap
            return takenImage
        } catch (e: Exception) {
            return null
        }
    }
}