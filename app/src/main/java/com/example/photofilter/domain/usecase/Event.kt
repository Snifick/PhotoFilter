package com.example.photofilter.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.photofilter.presentation.MainActivity

sealed class MyEvent{

    class PhotoIsReady(val context: Context) : MyEvent()
    class ChangeScreen(val newScreen:ScreenState) : MyEvent()
    class PickPhotoByStorage(val activity: MainActivity):MyEvent()
    class MakePhoto(val activity: MainActivity,val context: Context):MyEvent()
    class ToSavePhoto(val bitmap: Bitmap, val applicationContext: Context,val needRotate:Boolean):MyEvent()
    class SharePhoto(val context: Context, val uri: Uri?):MyEvent()

}

sealed class Action(){

    object filters : Action()
    object draw : Action()
    object loading:Action()
}
