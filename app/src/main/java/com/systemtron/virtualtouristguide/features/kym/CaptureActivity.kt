package com.systemtron.virtualtouristguide.features.kym

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.systemtron.virtualtouristguide.R
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_capture.*

class CaptureActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)

        tvName.visibility = View.GONE

        btnClick.setOnClickListener {
            CropImage.activity().start(this)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {
                val imageURI = result.uri
                analyzeImage(MediaStore.Images.Media.getBitmap(contentResolver, imageURI))
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Error : ${result.error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun analyzeImage(image: Bitmap?) {
        if (image == null) {
            Toast.makeText(this, "Error 2", Toast.LENGTH_SHORT).show()
            return
        }

        lr_imageview.visibility = View.VISIBLE
        lr_imageview.setImageBitmap(image)

        val firebaseVisionImage = FirebaseVisionImage.fromBitmap(image)
        val options = FirebaseVisionCloudDetectorOptions.Builder().setMaxResults(5).build()

        val landmarkDetector = FirebaseVision.getInstance().getVisionCloudLandmarkDetector(options)

        landmarkDetector.detectInImage(firebaseVisionImage).addOnSuccessListener {
            val mutableImage = image.copy(Bitmap.Config.ARGB_8888, true)

            recognizeLandmarks(it, mutableImage)
        }.addOnFailureListener {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recognizeLandmarks(landmarks: List<FirebaseVisionCloudLandmark>?, image: Bitmap?) {
        if(landmarks == null || image == null){
            Toast.makeText(this,"Error 3",Toast.LENGTH_SHORT).show()
            return
        }

        val name = landmarks[0].landmark

        tvName.visibility = View.VISIBLE
        tvName.text = name
    }
}

