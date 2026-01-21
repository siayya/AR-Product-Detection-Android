package com.example.assignment

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
private val detectedProductIds = HashSet<String>()

class ProductAnalyzer(
    private val overlayView: AROverlayView

) : ImageAnalysis.Analyzer {

    private val detector = ObjectDetection.getClient(
        ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .enableMultipleObjects()
            .enableClassification()
            .build()
    )
    private fun generateProductId(obj: DetectedObject): String {
        val centerX = obj.boundingBox.centerX()
        val centerY = obj.boundingBox.centerY()
        val label = obj.labels.firstOrNull()?.text ?: "unknown"

        return "$label-$centerX-$centerY"
    }

    private fun processDetections(objects: List<DetectedObject>) {

        val newRects = mutableListOf<android.graphics.Rect>()

        for (obj in objects) {

            val productId = generateProductId(obj)

            // 🔒 NON-DUPLICATION LOGIC
            if (detectedProductIds.contains(productId)) {
                continue   // already detected → ignore
            }

            // New product detected
            detectedProductIds.add(productId)
            newRects.add(obj.boundingBox)
        }

        if (newRects.isNotEmpty()) {
            overlayView.updateDetections(newRects)
        }
    }


    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        )

        detector.process(image)
            .addOnSuccessListener { objects ->
                processDetections(objects)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }


}


