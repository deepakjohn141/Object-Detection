package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.helpers.ObjectDetectorHelper
import com.example.myapplication.data.ImageRepository
import com.example.myapplication.data.ImageRepositoryImpl
import com.example.myapplication.viewmodels.CameraViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ImageRepository> { ImageRepositoryImpl() }

    viewModel { CameraViewModel(imageRepository =  get()) }

    factory { (context: Context, listener: ObjectDetectorHelper.DetectorListener)-> ObjectDetectorHelper(context =  context, objectDetectorListener =  listener) }
}