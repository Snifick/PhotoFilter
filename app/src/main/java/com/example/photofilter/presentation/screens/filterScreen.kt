package com.example.photofilter.presentation.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photofilter.R
import com.example.photofilter.domain.usecase.Action
import com.example.photofilter.domain.usecase.MyEvent
import com.example.photofilter.domain.usecase.ScreenState
import com.example.photofilter.presentation.AppViewModel
import io.getstream.sketchbook.PaintColorPalette
import io.getstream.sketchbook.PaintColorPaletteTheme
import io.getstream.sketchbook.Sketchbook
import io.getstream.sketchbook.rememberSketchbookController
import jp.co.cyberagent.android.gpuimage.GPUImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun filterScreen(appViewModel: AppViewModel = viewModel()) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        appViewModel.filteredBitmap.value = appViewModel.imageBitmap.value
    }
    val eraseMode = remember{
        mutableStateOf(false)
    }
    val sketchbookController = rememberSketchbookController()
    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = { Text("Редактор") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(
                    0xFFD85353
                ), titleContentColor = Color.White
            ),

            navigationIcon = {
                IconButton(onClick = { appViewModel.runEvent(MyEvent.ChangeScreen(ScreenState.homeScreen)) },
                    content = {
                        Icon(
                            painterResource(id = R.drawable.baseline_arrow_back_ios_new_48),
                            contentDescription = "", tint = Color.White
                        )
                    })
            },

            actions = {
                IconButton(onClick = {
                    if(appViewModel.currentAction.value == Action.draw){
                   appViewModel.filteredBitmap.value =
                       sketchbookController.getSketchbookBitmap().asAndroidBitmap()
                    }


                    appViewModel.runEvent(


                        MyEvent.ToSavePhoto(
                            appViewModel.filteredBitmap.value!!,
                            context,
                            false
                        )
                    )

                    appViewModel.runEvent(MyEvent.ChangeScreen(ScreenState.resultScreen))
                },
                    content = {
                        Icon(
                            painterResource(id = R.drawable.baseline_done_48),
                            contentDescription = "", tint = Color.White
                        )
                    })
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.DarkGray)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f), contentAlignment = Alignment.Center
            ) {
                when (appViewModel.currentAction.value) {
                    is Action.filters, Action.loading -> {
                        appViewModel.filteredBitmap.value?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = ""
                            )
                        }
                    }

                    is Action.draw -> {

                        Sketchbook(

                            modifier = Modifier.fillMaxSize(),
                            imageBitmap = appViewModel.filteredBitmap.value?.asImageBitmap(),
                            controller = sketchbookController,
                            backgroundColor = Color.White
                        )

                    }
                }

            }


            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = {
                    appViewModel.currentAction.value = Action.filters
                }) {
                    Text(text = "Фильтры")
                }
                OutlinedButton(onClick = { appViewModel.currentAction.value = Action.draw }) {
                    Text(text = "Рисование")
                }

            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            when (appViewModel.currentAction.value) {
                is Action.loading ->{
                    Box(modifier = Modifier.fillMaxSize().padding(8.dp), contentAlignment = Alignment.Center) {
                        Text(text = stringResource(id = R.string.loading), fontSize = 18.sp, color = Color.White )
                    }

                }
                is Action.filters -> {
                    val listState = rememberLazyListState()
                    LazyRow(
                        state = listState, modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        items(appViewModel.list.value) {
                            Card(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                shape = RoundedCornerShape(20),
                                onClick = {
                                    val gpuImage = GPUImage(context).apply {
                                        setImage(appViewModel.imageBitmap.value)
                                        setFilter(it.filter)
                                    }
                                    appViewModel.filteredBitmap.value =
                                        gpuImage.bitmapWithFilterApplied
                                }) {
                                Image(
                                    bitmap = it.filterPreview.asImageBitmap(),
                                    contentDescription = ""
                                )

                            }

                        }


                    }
                }

                is Action.draw -> {


                    
                    PaintColorPalette(
                        controller = sketchbookController,
                        theme = PaintColorPaletteTheme(
                            shape = CircleShape,
                            itemSize = 48.dp,
                            selectedItemSize = 58.dp,
                            borderColor = Color.White,
                            borderWidth = 2.dp,
                        ),
                        onColorSelected = { index, color ->
                            sketchbookController.setPaintColor(
                                color
                            )
                        }
                    )
                    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                           IconButton(onClick = { sketchbookController.undo() }) {
                               Icon(painter = painterResource(id = R.drawable.baseline_rotate_left_48), contentDescription = "")
                           }
                        IconButton(onClick = {
                            eraseMode.value = !eraseMode.value
                            sketchbookController.setEraseMode(eraseMode.value) }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_brush_48), contentDescription = "",
                            tint = if(eraseMode.value){Color.Red}else{Color.Green}

                            )
                        }

                    }
                }

            }


        }
    }
}






