# AR-Product-Detection-Android
#Android AR product scanner using CameraX and image analysis.
An Android application that uses CameraX, ImageAnalysis, and an AR overlay to detect products in real time and visually mark detected items with tick indicators.This project was developed as part of a technical assignment to demonstrate real-time camera processing and augmented-reality style UI overlays.

#Features
Real-time camera preview using CameraX
Live product detection with ImageAnalysis
AR overlay that places tick marks on detected products
Automatic frame processing with backpressure handling
Clean architecture using ViewBinding and lifecycle-aware components

#Technologies Used
Kotlin
Android CameraX
ImageAnalysis API
ViewBinding
AR Overlay View
Android Lifecycle Component

#App Workflow
Launch the app and open the camera preview.
The system scans the retail shelf in real time.
Detected products are automatically marked with a ✔️ tick overlay.
The overlay updates dynamically as new products appear in the frame.

#Project Structure
MainActivity.kt – Handles camera initialization and lifecycle binding
ProductAnalyzer.kt – Processes camera frames and performs detection logic
arOverlay – Custom view used to draw detection marks on the screen
ActivityMainBinding – ViewBinding for layout access
