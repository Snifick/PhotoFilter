package com.example.photofilter.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import com.example.photofilter.CAMERA_REQUEST_CODE
import com.example.photofilter.GALLERY_REQUEST_CODE
import com.example.photofilter.domain.usecase.MyEvent
import com.example.photofilter.domain.usecase.ScreenState
import com.example.photofilter.fileUri
import com.example.photofilter.presentation.screens.filterScreen
import com.example.photofilter.presentation.screens.homeScreen
import com.example.photofilter.presentation.screens.resultScreen
import com.example.photofilter.ui.theme.PhotoFilterTheme



class MainActivity : ComponentActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val viewModel:AppViewModel by viewModels()
        if (resultCode == Activity.RESULT_OK ) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    viewModel.lastSavedUri.value = data?.data
                    viewModel.imageBitmap.value = MediaStore.Images.Media.getBitmap(this.contentResolver,data?.data)
                   viewModel.runEvent(MyEvent.PhotoIsReady(applicationContext))
                }
                CAMERA_REQUEST_CODE -> {
                    val imageUri = fileUri
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    viewModel.runEvent(MyEvent.ToSavePhoto(bitmap, applicationContext, true))
                    viewModel.runEvent(MyEvent.PhotoIsReady(applicationContext))
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel:AppViewModel by viewModels()

        setContent {
            PhotoFilterTheme {

                when(viewModel.screenState.value){
                    ScreenState.homeScreen->{
                        homeScreen(activity = this, applicationContext = LocalContext.current)
                    }
                    ScreenState.filterScreen->{
                        filterScreen()
                    }
                    ScreenState.resultScreen->{
                        resultScreen(context = LocalContext.current)
                    }
                }

            }
        }
    }
}


