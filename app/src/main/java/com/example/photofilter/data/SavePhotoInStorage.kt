package com.example.photofilter.data

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.example.photofilter.domain.repository.SavePhotoRepository


class SavePhotoInStorage : SavePhotoRepository{
    override fun savePhoto(bitmap: Bitmap,applicationContext: Context): Uri? {

            val filename = "${System.currentTimeMillis()}.jpg"
            val resolver = applicationContext.contentResolver
            val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.WIDTH, bitmap.width)
                put(MediaStore.Images.Media.HEIGHT, bitmap.height)
            }
        val uri = resolver.insert(imageUri, contentValues)
        uri?.let {
            resolver.openOutputStream(it)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            return it
        }
        return null
        }

}