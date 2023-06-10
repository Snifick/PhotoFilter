package com.example.photofilter.domain.repository

import android.content.Context
import android.net.Uri

interface SharePhoto {
    fun sharePhoto(context: Context, uri: Uri?)
}