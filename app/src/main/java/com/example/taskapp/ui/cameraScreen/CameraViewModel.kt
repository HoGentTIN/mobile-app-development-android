package com.example.taskapp.ui.cameraScreen

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


/*
* CameraViewModel: holds some additional state for the camera features
* - permissionState: boolean to check if permissions are ok
* - imageCapture: a class from the camera lib that contains the takePicture() method
*
* */
class CameraViewModel: ViewModel() {

    var currentpermissionState = mutableStateOf(false)

    //this variable will hold the picture
    var imageCapture: MutableState<ImageCapture>

    init {
        Log.i("vm inspection", "CameraViewModel init")

        //the container for the image is prepared here
        imageCapture = mutableStateOf(ImageCapture.Builder().build())
    }

    fun setPermissionState(granted: Boolean){
        currentpermissionState.value = granted
    }


    override fun onCleared() {
        super.onCleared()
        Log.i("vm inspection", "CameraViewModel cleared")

    }

    val REQUIRED_PERMISSIONS =
        mutableListOf (
            Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

}
