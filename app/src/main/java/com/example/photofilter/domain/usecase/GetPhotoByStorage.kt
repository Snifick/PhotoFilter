package com.example.photofilter.domain.usecase

import com.example.photofilter.data.StoragePhoto
import com.example.photofilter.presentation.MainActivity

class GetPhotoByStorage() {
    fun getPhotoByStorage(activity: MainActivity){
       val storagePhoto = StoragePhoto()
        storagePhoto.getPhotoByStorage(activity)

    }
}