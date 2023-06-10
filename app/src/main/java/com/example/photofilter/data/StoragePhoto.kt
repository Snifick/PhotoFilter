package com.example.photofilter.data

import android.content.Intent

import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.photofilter.GALLERY_REQUEST_CODE
import com.example.photofilter.domain.repository.getPhotoByStorageRepository
import com.example.photofilter.presentation.MainActivity

class StoragePhoto : getPhotoByStorageRepository {
    override fun getPhotoByStorage(activity: MainActivity) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(activity,intent, GALLERY_REQUEST_CODE,null)

    }

}