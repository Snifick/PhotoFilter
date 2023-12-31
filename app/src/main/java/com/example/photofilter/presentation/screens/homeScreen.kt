package com.example.photofilter.presentation.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.PermissionChecker
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photofilter.R
import com.example.photofilter.domain.models.MyEvent

import com.example.photofilter.presentation.AppViewModel
import com.example.photofilter.presentation.MainActivity



@Composable
fun homeScreen(appViewModel: AppViewModel = viewModel(), activity: MainActivity, applicationContext: Context) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.DarkGray),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally) {

        val context = LocalContext.current
        val permissionState = remember { mutableStateOf(PermissionChecker.checkSelfPermission(context, android.Manifest.permission.CAMERA)) }
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            permissionState.value = if (isGranted) PermissionChecker.PERMISSION_GRANTED else PermissionChecker.PERMISSION_DENIED
        }

        ModernCard(text = stringResource(id = R.string.getStoragePhoto) ,
            onClick ={ appViewModel.runEvent(MyEvent.PickPhotoByStorage(activity))} )

        ModernCard(text =stringResource(id = R.string.getcameraPhoto), onClick ={
                if (PermissionChecker.checkSelfPermission(
                        context,
                        android.Manifest.permission.CAMERA
                    ) == PermissionChecker.PERMISSION_GRANTED
                ) {
                    appViewModel.runEvent(MyEvent.MakePhoto(activity,applicationContext))
                } else {
                    Toast.makeText(context, "no permission", Toast.LENGTH_SHORT).show()
                    launcher.launch(android.Manifest.permission.CAMERA)
                }
            }  )






    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernCard(text:String,onClick:()->Unit) {
    Card(modifier = Modifier.size(180.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                0xFFD85353
            )
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick.invoke() }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 18.sp, color = Color.White, textAlign = TextAlign.Center
            )
        }

    }
}

