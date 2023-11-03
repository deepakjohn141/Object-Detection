package com.example.myapplication.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ImageRepository
import com.example.myapplication.data.Result
import com.example.myapplication.utilities.Schedulers
import kotlinx.coroutines.launch

class CameraViewModel (private val imageRepository: ImageRepository) : ViewModel() {


    private val _saveImageResult: MutableLiveData<Result<Unit>?> = MutableLiveData()
    private val saveImageResult: LiveData<Result<Unit>?> = _saveImageResult

    fun getImageResult() = saveImageResult
    fun savePhoto(previewBitmap: Bitmap, overlayBitmap: Bitmap) {
        _saveImageResult.postValue(null)
        viewModelScope.launch(Schedulers.IO) {
            val result = imageRepository.saveImage(previewBitmap, overlayBitmap)
            _saveImageResult.postValue(result)
        }
    }


}
