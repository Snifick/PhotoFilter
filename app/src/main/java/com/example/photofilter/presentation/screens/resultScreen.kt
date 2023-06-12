package com.example.photofilter.presentation.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photofilter.R
import com.example.photofilter.domain.models.MyEvent
import com.example.photofilter.domain.models.ScreenState

import com.example.photofilter.presentation.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun resultScreen(appViewModel: AppViewModel = viewModel(), context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        TopAppBar(
            title = { Text("Готово!") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(
                    0xFFD85353
                ), titleContentColor = Color.White
            ),

            navigationIcon = {
                IconButton(onClick = {
                    appViewModel.runEvent(
                        MyEvent.ChangeScreen(
                            ScreenState.FilterScreen
                        )
                    )
                },
                    content = {
                        Icon(
                            painterResource(id = R.drawable.baseline_arrow_back_ios_new_48),
                            contentDescription = "", tint = Color.White
                        )
                    })
            },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            appViewModel.filteredBitmap.value?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth().height(480.dp)
                )
            }

            Text(text = stringResource(id = R.string.completedSave), color = Color.White)

            OutlinedButton(
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp), onClick ={
                        appViewModel.runEvent(MyEvent.SharePhoto(context, appViewModel.lastSavedUri.value))
                }
            ) {
                Text(text = stringResource(id = R.string.shareWithFriends), color = Color.White)
            }
        }
    }
}
