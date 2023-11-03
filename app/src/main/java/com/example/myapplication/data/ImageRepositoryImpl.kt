package com.example.myapplication.data

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// The ImageRepositoryImpl class implements the ImageRepository interface.
class ImageRepositoryImpl : ImageRepository {
    private val FAILED_MESSAGE = "Failed to save the image"
    private val SUCCESS_MESSAGE = "Saved image in "

    // Save the image with overlay.
    override suspend fun saveImage(previewBitmap: Bitmap, overlayBitmap: Bitmap): Result<Unit> {
        var imageSavedResult: Result<Unit>? = null

        // Create a canvas to draw the overlay bitmap on the preview bitmap.
        val canvas = Canvas(previewBitmap)
        canvas.drawBitmap(
            overlayBitmap,
            Rect(0, 0, overlayBitmap.getWidth(), overlayBitmap.getHeight()),
            Rect(0, 0, previewBitmap.getWidth(), previewBitmap.getHeight()),
            null
        )


        // Save the preview bitmap with overlay.


        // Create a FileOutputStream object to write the pixels to a file.
        val file = createImageFile()
        val fos = FileOutputStream(file)
        imageSavedResult = try {
            val compressed = previewBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            if (compressed) {
                Result.Success("${SUCCESS_MESSAGE} ${file.absolutePath}")
            } else {
                Result.Error(FAILED_MESSAGE)
            }
        } catch (e: Exception) {
            Result.Error(FAILED_MESSAGE)
        } finally {
            fos.close()
        }

        return imageSavedResult!!

    }

    private fun createImageFile(): File {
        val sdf = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.US)
        return File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath,
            "IMG_${
                sdf.format(
                    Date()
                )
            }.png"
        )
    }
}