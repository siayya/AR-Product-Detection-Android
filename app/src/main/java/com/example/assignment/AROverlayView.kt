package com.example.assignment   // MUST MATCH YOUR PACKAGE

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class AROverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val boxPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 6f
        isAntiAlias = true
    }

    private val tickPaint = Paint().apply {
        color = Color.GREEN
        textSize = 48f
        isAntiAlias = true
    }

    private val detectedRects = mutableListOf<Rect>()

    fun updateDetections(rects: List<Rect>) {
        detectedRects.clear()
        detectedRects.addAll(rects)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (rect in detectedRects) {
            canvas.drawRect(rect, boxPaint)
            canvas.drawText(
                "✔",
                rect.right.toFloat(),
                rect.top.toFloat(),
                tickPaint
            )
        }
    }
}
