package com.example.photofilter.domain.usecase

import android.content.Context
import com.example.photofilter.data.CameraPhoto
import com.example.photofilter.presentation.MainActivity

class GetPhotoByCamera {
    fun getPhotoByCamera(activity: MainActivity,context: Context){
        val cameraPhoto = CameraPhoto()
        cameraPhoto.getPhotoByCamera(activity,context)

    }
}