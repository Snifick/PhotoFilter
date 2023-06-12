package com.example.photofilter.domain.repository

import android.content.Context
import com.example.photofilter.presentation.MainActivity

interface GetPhotoByCameraRepository {
    fun getPhotoByCamera(activity: MainActivity,context: Context)
}