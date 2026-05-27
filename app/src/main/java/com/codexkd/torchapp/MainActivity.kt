package com.codexkd.torchapp

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : Activity() {

private lateinit var cameraManager: CameraManager
private var cameraId: String? = null
private var isTorchOn = false

override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)

setContentView(R.layout.activity_main)

val torchButton = findViewById<Button>(R.id.torchButton)

// Check flashlight support
if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {

Toast.makeText(
this,
"Flashlight not supported",
Toast.LENGTH_LONG
).show()

return
}

cameraManager = getSystemService(
Context.CAMERA_SERVICE
) as CameraManager

try {
cameraId = cameraManager.cameraIdList[0]
} catch (e: Exception) {
e.printStackTrace()
}

torchButton.setOnClickListener {
toggleFlashlight()
}
}

private fun toggleFlashlight() {

try {

cameraId?.let {

isTorchOn = !isTorchOn

cameraManager.setTorchMode(
it,
isTorchOn
)
}

} catch (e: Exception) {

e.printStackTrace()

Toast.makeText(
this,
"Torch error",
Toast.LENGTH_SHORT
).show()
}
}

override fun onDestroy() {
super.onDestroy()

try {

cameraId?.let {
cameraManager.setTorchMode(it, false)
}

} catch (e: Exception) {
e.printStackTrace()
}
}
}