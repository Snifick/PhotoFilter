package com.example.photofilter.domain.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.example.photofilter.domain.repository.SharePhoto

class SharePhotoImpl :SharePhoto {
    override fun sharePhoto(context: Context,uri: Uri?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/jpeg"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)

    }

}