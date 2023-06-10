package com.example.photofilter.domain.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

interface savePhotoRepository {
    fun savePhoto(bitmap: Bitmap,applicationContext: Context): Uri?
}