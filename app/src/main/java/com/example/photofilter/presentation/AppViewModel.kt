package com.example.photofilter.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photofilter.domain.models.ImageFilter
import com.example.photofilter.domain.models.Action
import com.example.photofilter.domain.models.MyEvent
import com.example.photofilter.domain.usecase.GetPhotoByCamera
import com.example.photofilter.domain.usecase.GetPhotoByStorage
import com.example.photofilter.domain.usecase.SavePhoto
import com.example.photofilter.domain.models.ScreenState
import com.example.photofilter.domain.usecase.SharePhotoImpl
import com.lacolinares.jetpicexpress.presentation.ui.editimage.mapper.EditImageMapperImpl
import jp.co.cyberagent.android.gpuimage.GPUImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel:ViewModel() {
    private val getPhotoStorage = GetPhotoByStorage()
    private val getPhotoCamera = GetPhotoByCamera()
    private val savePhoto = SavePhoto()
    private  val sharePhotoImpl = SharePhotoImpl()
    private val editImageMapper =  EditImageMapperImpl()
    val lastSavedUri = mutableStateOf<Uri?>(null)
    val imageBitmap: MutableState<Bitmap?> = mutableStateOf(null)
    val filteredBitmap: MutableState<Bitmap?> = mutableStateOf(null)
    val screenState: MutableState<ScreenState> = mutableStateOf( ScreenState.HomeScreen)
    val currentAction:MutableState<Action> = mutableStateOf(Action.loading)

    val list = mutableStateOf<List<ImageFilter>>( listOf())


    fun runEvent(event: MyEvent){
        when(event){
            is MyEvent.ChangeScreen ->{
                screenState.value = event.newScreen
            }
            is MyEvent.PickPhotoByStorage ->{
              getPhotoStorage.getPhotoByStorage(event.activity)
            }
            is MyEvent.MakePhoto ->{
                getPhotoCamera.getPhotoByCamera(event.activity, event.context)
            }

            is MyEvent.PhotoIsReady->{
                CoroutineScope(Dispatchers.Default).launch{
                    val gpuImage = GPUImage(event.context).apply {
                        setImage(imageBitmap.value)
                    }
                   list.value = editImageMapper.mapToImageFilters(gpuImage)
                    currentAction.value = Action.filters
                }
                runEvent(MyEvent.ChangeScreen(ScreenState.FilterScreen))
            }
            is MyEvent.ToSavePhoto->{
                savePhoto(event.bitmap,event.applicationContext, event.needRotate)
            }
            is MyEvent.SharePhoto ->{
                sharePhotoImpl.sharePhoto(event.context, event.uri)
            }

        }
    }

    fun savePhoto(bitmap: Bitmap, context: Context, needRotate:Boolean){
        if(needRotate){
        val matrix = Matrix()
        matrix.postRotate(90f) // Поворот на 90 градусов, так как изначально фото горизонтальное
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        lastSavedUri.value = savePhoto.savePhoto(rotatedBitmap,context)
        imageBitmap.value = rotatedBitmap}
        else{
            lastSavedUri.value = savePhoto.savePhoto(bitmap,context)
            imageBitmap.value = bitmap
        }
    }


}