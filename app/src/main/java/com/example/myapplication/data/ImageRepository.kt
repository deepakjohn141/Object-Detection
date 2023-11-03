package com.example.myapplication.data

import android.graphics.Bitmap

 fun interface ImageRepository {

   suspend fun saveImage(previewBitmap: Bitmap, overlayBitmap: Bitmap): Result<Unit>
}