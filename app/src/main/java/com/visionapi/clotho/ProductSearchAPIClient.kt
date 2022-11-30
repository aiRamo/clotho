package com.visionapi.clotho

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.visionapi.clotho.databinding.CameraToolBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.google.mlkit.vision.objects.defaults.PredefinedCategory
import kotlinx.android.synthetic.main.camera_tool.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.abs
import kotlin.math.max

class ProductSearchAPIClient : AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1000
        private const val REQUEST_IMAGE_GALLERY = 1001
        private const val TAKEN_BY_CAMERA_FILE_NAME = "MLKitDemo_"
        private const val IMAGE_PRESET_2 = "Preset2.png"
        private const val TAG = "MLKit-ODT"
    }

    private lateinit var viewBinding: CameraToolBinding
    private var cameraPhotoUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = CameraToolBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // After taking camera, display to Preview
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> cameraPhotoUri?.let {
                    this.setViewAndDetect(
                        getBitmapFromUri(it)
                    )
                }
                REQUEST_IMAGE_GALLERY -> data?.data?.let { this.setViewAndDetect(getBitmapFromUri(it)) }
            }
        }
    }

    private fun initViews() {
        with(viewBinding) {
            // display

            btnRetakePhoto.setOnClickListener { dispatchTakePictureIntent() }
            ivPreview.setOnObjectClickListener { objectImage ->
                startProductImageSearch(objectImage)
            }

            setViewAndDetect(getBitmapFromAsset(IMAGE_PRESET_2))
        }
    }

    private fun setViewAndDetect(bitmap: Bitmap?) {
        bitmap?.let {
            // Clear the dots indicating the previous detection result
            viewBinding.ivPreview.drawDetectionResults(emptyList())

            // Display the input image on the screen.
            viewBinding.ivPreview.setImageBitmap(bitmap)

            // Run object detection and show the detection results.
            runObjectDetection(bitmap)
        }
    }

    private fun getBitmapFromAsset(fileName: String): Bitmap? {
        return try {
            BitmapFactory.decodeStream(assets.open(fileName))
        } catch (ex: IOException) {
            null
        }
    }

    private fun runObjectDetection(bitmap: Bitmap) {
        // Step 1: create ML Kit's InputImage object
        val image = InputImage.fromBitmap(bitmap, 0)

        // Step 2: acquire detector object
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()
            .build()
        val objectDetector = ObjectDetection.getClient(options)

        // Step 3: feed given image to detector and setup callback
        objectDetector.process(image)
            .addOnSuccessListener { results ->
                // Keep only the FASHION_GOOD objects
                val filteredResults = results.filter { result ->
                    result.labels.indexOfFirst { it.text == PredefinedCategory.FASHION_GOOD } != -1
                }

                // Visualize the detection result
                runOnUiThread {
                    viewBinding.ivPreview.drawDetectionResults(filteredResults)
                }

            }
            .addOnFailureListener {
                // Task failed with an exception
                Log.e(TAG, it.message.toString())
            }

    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile(TAKEN_BY_CAMERA_FILE_NAME)
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    cameraPhotoUri = FileProvider.getUriForFile(
                        this,
                        "com.visionapi.clotho.fileprovider",
                        it
                    )
                    // Setting output file to take a photo
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPhotoUri)
                    // Open camera based Intent.
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            } ?: run {
                Toast.makeText(this, getString(R.string.camera_app_not_found), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(fileName: String): File {
        // Create an image file name
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            fileName, /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun getBitmapFromUri(imageUri: Uri): Bitmap? {
        val bitmap = try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, imageUri))
            } else {
                // Add Suppress annotation to skip warning by Android Studio.
                // This warning resolved by ImageDecoder function.
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            }
        } catch (ex: IOException) {
            null
        }

        // Make a copy of the bitmap in a desirable format
        return bitmap?.copy(Bitmap.Config.ARGB_8888, false)
    }

    private fun startProductImageSearch(objectImage: Bitmap) {
        try {
            // Create file based Bitmap. We use PNG to preserve the image quality
            val savedFile = createImageFile(ProductSearchActivity.CROPPED_IMAGE_FILE_NAME)
            objectImage.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(savedFile))

            // Start the product search activity (using Vision Product Search API.).
            startActivity(
                Intent(
                    this,
                    ProductSearchActivity::class.java
                ).apply {
                    // As the size limit of a bundle is 1MB, we need to save the bitmap to a file
                    // and reload it in the other activity to support large query images.
                    putExtra(
                        ProductSearchActivity.REQUEST_TARGET_IMAGE_PATH,
                        savedFile.absolutePath
                    )
                })
        } catch (e: Exception) {
            // IO Exception, Out Of memory ....
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Error starting the product image search activity.", e)
        }
    }

}