package com.example.parkinsonvethaichi

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView
import com.example.parkinsonvethaichi.ml.LiteModelMovenetSingleposeLightningTfliteFloat164
import kotlinx.android.synthetic.main.activity_movement_control.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MovementControl : AppCompatActivity() {
    val paint = Paint()
    private lateinit var imageProcessor : ImageProcessor
    lateinit var model: LiteModelMovenetSingleposeLightningTfliteFloat164
    private lateinit var bitmap:Bitmap
    private lateinit var handler:Handler
    private lateinit var handlerThread: HandlerThread
    private lateinit var cameraManager: CameraManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movement_control)
        getPermission()

        imageProcessor = ImageProcessor.Builder().add(ResizeOp(192,192,ResizeOp.ResizeMethod.BILINEAR)).build()

        model = LiteModelMovenetSingleposeLightningTfliteFloat164.newInstance(this)
        cameraManager=getSystemService(Context.CAMERA_SERVICE) as CameraManager
        handlerThread= HandlerThread("videoThread")
        handlerThread.start()
        handler=Handler(handlerThread.looper)
        paint.setColor(Color.RED)
        texture_view.surfaceTextureListener=object :TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                openCamera()
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {

            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                bitmap = texture_view.bitmap!!
                var tensorImage = TensorImage(DataType.UINT8)
                tensorImage.load(bitmap)
                tensorImage = imageProcessor.process(tensorImage)



// Creates inputs for reference.
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 192, 192, 3), DataType.UINT8)
                inputFeature0.loadBuffer(tensorImage.buffer)


                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

                var mutable = bitmap.copy(Bitmap.Config.ARGB_8888,true)
                var canvas = Canvas(mutable)

                var h = bitmap.height
                var w = bitmap.width
                var x = 0
                val radius = 10f
                val lineThickness = 5f

                while(x <= 49){
                    if(outputFeature0.get(x+2) > 0.45){
                        val cx = outputFeature0.get(x+1) * w
                        val cy = outputFeature0.get(x) * h
                        if (x !in 0..12) {
                            canvas.drawCircle(cx, cy, radius, paint)
                        }
                        //canvas.drawCircle(cx, cy, radius, paint)
                    }
                    x += 3
                }

                // Draw lines between points
                val connections = arrayOf(
                    Pair(9,7), //Sol El Bileği-Sol Dirsek
                    Pair(7,5), //Sol Dirsek-Sol Omuz
                    Pair(5, 6),  // Sol Omuz- Sağ Omuz
                    Pair(6, 8),  // Sağ Omuz- Sağ Dirsek
                    Pair(8, 10),  // Sağ Dirsek - Sağ El Bileği
                    Pair(5, 11), // Sol Omuz-Sol Kalça
                    Pair(11, 13),// Sol Kalça- Sol Diz
                    Pair(13, 15),  //Sol Diz- Sol Ayak Bileği
                    Pair(6, 12),  // Sağ Omuz- Sağ Kalça
                    Pair(12, 14),  //Sağ Kalça- Sağ Diz
                    Pair(14, 16),  //Sağ Diz - Sağ Ayak Bileği
                    Pair(11, 12),  //Sol Kalça- Sağ Kalça

                )

                paint.strokeWidth = lineThickness

                for (connection in connections) {
                    val startIndex = connection.first * 3
                    val endIndex = connection.second * 3

                    if (startIndex >= outputFeature0.size || endIndex >= outputFeature0.size) {
                        continue
                    }

                    val startX = outputFeature0[startIndex + 1] * w
                    val startY = outputFeature0[startIndex] * h
                    val endX = outputFeature0[endIndex + 1] * w
                    val endY = outputFeature0[endIndex] * h

                    canvas.drawLine(startX, startY, endX, endY, paint)
                }

                imageView.setImageBitmap(mutable)
// Releases model resources if no longer used.


            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        model.close()
    }

    @SuppressLint("MissingPermission")
    private fun openCamera(){
        cameraManager.openCamera(cameraManager.cameraIdList[0],object:CameraDevice.StateCallback(){
            override fun onOpened(p0: CameraDevice) {
                var captureRequest=p0.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                var surface= Surface(texture_view.surfaceTexture)
                captureRequest.addTarget(surface)
                p0.createCaptureSession(listOf(surface),object: CameraCaptureSession.StateCallback(){
                    override fun onConfigured(p0: CameraCaptureSession) {
                        p0.setRepeatingRequest(captureRequest.build(),null,null)
                    }

                    override fun onConfigureFailed(p0: CameraCaptureSession) {

                    }
                                                                                                    },handler)
            }

            override fun onDisconnected(p0: CameraDevice) {

            }

            override fun onError(p0: CameraDevice, p1: Int) {

            }
                                                                                                  },handler)
    }


    private fun getPermission() {
        if(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        ){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA),101)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]!= PackageManager.PERMISSION_GRANTED)getPermission()
    }
}