package com.cristhianbonilla.com.artistasamerica.ui.activities.opencv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import com.cristhianbonilla.com.artistasamerica.R
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.JavaCameraView
import org.opencv.android.OpenCVLoader
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class CameraActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {
    companion object {
        const val OPENCV_TAG = "OPENCV"
    }

    private var mRgba: Mat? = null
    private var mRgbat: Mat? = null

    private var baseloaderCallBack: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {

            when (status) {
                BaseLoaderCallback.SUCCESS -> {
                    cameraview.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }


    private lateinit var cameraview: JavaCameraView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        initView()

        cameraview.setCvCameraViewListener(this)
    }

    private fun initView() {
        cameraview = findViewById<JavaCameraView>(R.id.my_camera)
        cameraview.visibility = SurfaceView.VISIBLE
    }

    override fun onCameraViewStarted(width: Int, height: Int) {

        mRgba = Mat(width,width,CvType.CV_8UC4)
    }

    override fun onCameraViewStopped() {
        mRgba?.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat? {

        mRgba = inputFrame?.rgba()
        mRgbat = mRgba?.t()
        Core.flip(mRgba?.t(),mRgbat,2)
        Imgproc.resize(mRgbat,mRgbat,mRgba?.size())

        return mRgbat
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraview?.let {it.disableView()}
    }

    override fun onPause() {
        super.onPause()
        cameraview?.let {it.disableView()}
    }

    override fun onResume() {
        super.onResume()

        if (OpenCVLoader.initDebug()) {
            Log.d(OPENCV_TAG, "Open Cv is configured or connected Succesfully")
            baseloaderCallBack.onManagerConnected(BaseLoaderCallback.SUCCESS)
        } else {
            Log.d(OPENCV_TAG, "OpenCv is not working or Loaded ")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this,baseloaderCallBack)
        }

    }
}
