package com.example.photofilter.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.photofilter.data.SavePhotoInStorage

class SavePhoto {
    fun savePhoto(bitmap: Bitmap, applicationContext: Context): Uri? {
        val savePhoto = SavePhotoInStorage()
        return savePhoto.savePhoto(bitmap, applicationContext)
    }
}