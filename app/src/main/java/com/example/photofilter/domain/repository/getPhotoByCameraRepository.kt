package com.example.photofilter.domain.repository

import android.content.Context
import com.example.photofilter.presentation.MainActivity

interface getPhotoByCameraRepository {
    fun getPhotoByCamera(activity: MainActivity,context: Context)
}