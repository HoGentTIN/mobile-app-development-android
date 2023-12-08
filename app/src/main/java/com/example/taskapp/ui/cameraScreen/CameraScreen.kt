package com.example.taskapp.ui.cameraScreen

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun CameraScreen(vm : CameraViewModel = viewModel()) {

    val context = LocalContext.current

    //permission state
    val permissionState = remember{ vm.currentpermissionState }

    vm.setPermissionState(
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED)


    // prepare code to request permissions
    val resultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in vm.REQUIRED_PERMISSIONS && it.value == false)
                    permissionGranted = false
            }
            vm.setPermissionState(permissionGranted)
        }
    )


    //camera can be started if permission is granted
    if (permissionState.value) {
        Log.i("cam", "show")
        //the camera view is shown
        cameraView(imageCapture = vm.imageCapture.value)
    } else {
        Text("waiting for permissions...")
        //checks the permission only on the first composition
        LaunchedEffect(true) {
            resultLauncher.launch(vm.REQUIRED_PERMISSIONS)
        }

    }
}

/*this composable starts an android view
* it accesses the camera and binds to the views lifecycle
* the preview (camera screen) is a class provided by AndroidX
* */
@Composable
fun cameraView(modifier: Modifier = Modifier,
               imageCapture: ImageCapture
               ) {

    AndroidView({ context ->

        //create the view: PreviewView
        //note: a view is a layout component, just like a LinearLayout or RecyclerView
        val previewView = PreviewView(context).also {
            it.scaleType = PreviewView.ScaleType.FILL_CENTER
        }

        /*
        * CameraProvider is an object that allows control over the camera
        * it requires a Preview instance
        * image capture or video capture use cases can be added too.
        * */

        /*The provider is found using a Future, a callback mechanism that works async
        * the .get() fetches the result (and could block if needed!)*/
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview instance
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    context as ComponentActivity, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e("cam", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(context))
        previewView
    })

    var context = LocalContext.current
    Button(onClick = { takePhoto(imageCapture, context = context)},
        content = { Text(text = "capture")}
    )

}

/*
* Helper function: sets up the options for the takePicture() function
* */
private fun takePhoto(imageCapture: ImageCapture, context: Context){

    val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    // Create time stamped name and MediaStore entry.
    val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
        .format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/TaskApp")
        }
    }

    /* Create output options object which contains file + metadata
        here, the options describe the MediaStore (= on disk)
        the contentValues are MediaStore metatags
     */
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues)
        .build()

    /*
    * takePicture returns immedialy but sets up the callback for pictures
    * output options describe how to store the image (in memory, on disk...)
    * executor describes a thread to run the callback
    * onImageSavedCallback describes what to do after the image is stored
    * */
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e("img capture", "Photo capture failed: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults){
                val msg = "Photo capture succeeded: ${output.savedUri}"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                Log.d("img capture", msg)
            }
        }
    )
}




