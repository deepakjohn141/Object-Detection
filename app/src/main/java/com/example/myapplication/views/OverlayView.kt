package com.example.myapplication.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R
import java.util.LinkedList
import kotlin.math.max
import org.tensorflow.lite.task.vision.detector.Detection

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results: List<Detection> = LinkedList<Detection>() // Detected objects
    private var boxPaints: ArrayList<Paint> = ArrayList() // For drawing bounding boxes
    private var textBackgroundPaint = Paint() // For drawing bounding box text background
    private var textPaint = Paint() // For drawing bounding box text

    private var scaleFactor: Float = 1f

    private var bounds = Rect()

    init {
        initPaints()
    }

    fun clear() {
        invalidate()
    }

    // Initialize paint objects
    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

        // Create a list of solid color paint objects to be used for drawing the bounding boxes.
        for (i in 0 .. 11) {
            val boxPaint = Paint()
            boxPaint.color = Color.parseColor(resources.getStringArray(R.array.bounding_colors)[i])
            boxPaint.strokeWidth = 8F
            boxPaint.style = Paint.Style.STROKE
            boxPaints.add(boxPaint) // Add solid color paint to list
        }


    }

    // Get a paint object for the given label.
    private fun getPaint(label: String): Paint {
        return when (label) {
            in PERSON -> boxPaints[0]
            in TRANSPORTATION -> boxPaints[1]
            in TRAFFIC_EQUIPMENT -> boxPaints[2]
            in ACCESSORIES -> boxPaints[3]
            in ANIMALS -> boxPaints[4]
            in SPORTS_EQUIPMENTS -> boxPaints[5]
            in TABLEWARE -> boxPaints[6]
            in FOOD -> boxPaints[7]
            in FURNITURE -> boxPaints[8]
            in ELECTRONICS -> boxPaints[9]
            in MISC -> boxPaints[10]
            else -> boxPaints[11]
        }
    }



    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        for (result in results) {
            val boundingBox = result.boundingBox

            val top = boundingBox.top * scaleFactor
            val bottom = boundingBox.bottom * scaleFactor
            val left = boundingBox.left * scaleFactor
            val right = boundingBox.right * scaleFactor

            val boxPaint = getPaint(result.categories[0].label)

            // Draw bounding box around detected objects
            val drawableRect = RectF(left, top, right, bottom)
            canvas.drawRect(drawableRect, boxPaint)

            // Create text to display alongside detected objects
            val drawableText =
                result.categories[0].label + " " +
                        String.format("%.2f", result.categories[0].score)

            // Draw rect behind display text
            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            canvas.drawRect(
                left,
                top,
                left + textWidth + BOUNDING_RECT_TEXT_PADDING,
                top + textHeight + BOUNDING_RECT_TEXT_PADDING,
                textBackgroundPaint
            )

            // Draw text for detected object
            canvas.drawText(drawableText, left, top + bounds.height(), textPaint)
        }
    }

    fun setResults(
      detectionResults: MutableList<Detection>,
      imageHeight: Int,
      imageWidth: Int,
    ) {
        results = detectionResults

        // PreviewView is in FILL_START mode. So we need to scale up the bounding box to match with
        // the size that the captured images will be displayed.
        scaleFactor = max(width * 1f / imageWidth, height * 1f / imageHeight)
    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
        private val PERSON = "person"
        private val TRANSPORTATION = listOf("bicycle", "car", "motorcycle", "airplane", "bus", "train", "truck", "boat")
        private val TRAFFIC_EQUIPMENT = listOf("traffic light", "fire hydrant", "stop sign", "parking meter")
        private val ACCESSORIES = listOf("backpack", "umbrella", "handbag", "tie", "suitcase")
        private val ANIMALS = listOf("bird", "cat", "dog", "horse", "sheep", "cow", "elephant", "bear", "zebra", "giraffe")
        private val SPORTS_EQUIPMENTS = listOf("frisbee", "skis", "snowboard", "sports ball", "kite", "baseball bat", "baseball glove", "skateboard", "surfboard", "tennis racket")
        private val TABLEWARE = listOf("bottle, wine glass", "cup", "fork", "knife", "spoon", "bowl")
        private val FOOD = listOf("banana", "apple", "sandwich", "orange", "broccoli", "carrot", "hot dog", "pizza", "donut", "cake")
        private val FURNITURE = listOf("chair", "couch", "bed", "dining table", "toilet", "sink", "bench")
        private val ELECTRONICS = listOf("tv", "laptop", "mouse", "remote", "keyboard", "cell phone", "microwave", "oven", "toaster", "refrigerator")
        private val MISC = listOf("book", "clock", "vase", "scissors", "teddy bear", "hair drier", "toothbrush", "potted plant")
    }
}
