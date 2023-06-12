package com.example.photofilter.data

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.photofilter.CAMERA_REQUEST_CODE
import com.example.photofilter.domain.repository.GetPhotoByCameraRepository
import com.example.photofilter.presentation.AppViewModel
import com.example.photofilter.presentation.MainActivity
import java.io.File


class CameraPhoto :GetPhotoByCameraRepository {
    override fun getPhotoByCamera(activity: MainActivity,context: Context) {

        val appViewModel:AppViewModel  = ViewModelProvider(activity)[AppViewModel::class.java]

        val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            "my_image",
            ".jpg",
            imagesDir
        )


        val imageURI = FileProvider.getUriForFile(
            context,
            "com.example.photofilter.fileprovider",
            imageFile
        )

        appViewModel.lastSavedUri.value = imageURI

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
        startActivityForResult(activity,cameraIntent, CAMERA_REQUEST_CODE,null)


}

}
