package com.example.photofilter.domain.repository

import com.example.photofilter.presentation.MainActivity

interface GetPhotoByStorageRepository {
    fun getPhotoByStorage(activity: MainActivity)
}