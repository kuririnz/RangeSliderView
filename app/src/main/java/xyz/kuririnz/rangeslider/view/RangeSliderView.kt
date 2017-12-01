package xyz.kuririnz.rangeslider.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by keisuke_kuribayashi on 2017/11/29.
 */
class RangeSliderView(context: Context
                      , attributeSet: AttributeSet? = null
                      , defStyleAttr: Int = 0) : View(context, attributeSet, defStyleAttr) {

    private val DEFAULT_WIDTH = 500
    private val DEFAULT_HEIGHT = 150
    private val THUMBS_TAG_FIRST = "FIRST_THUMB"
    private val THUMBS_TAG_SECOND = "SECOND_THUMB"

    private val c = context
    private var thumbFir = ThumbView(c)
    private var thumbSec = ThumbView(c)
    private var activeBar = ActiveBarView(c)
    private var backBar = Paint()
    private var backBarRect = RectF()
    private var positions = arrayOf(0f, 0f)
    private var scalePositions = arrayOf(0, 9)
    private var scaleCount = 10
    private var max = 9
    private var min = 0

    lateinit var changeListener : RangeChangeListerner

    /*====================================================
    // Secondary Constructor
    ====================================================*/
    constructor(context: Context, attributeSet: AttributeSet): this(context, attributeSet, 0)

    constructor(context: Context): this(context, null)

    /*====================================================
    // Life Cycle Methods
    ====================================================*/
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        println("onSizeChanged confirm w: ${w}, h: ${h}, old data -> ${oldw} : ${oldh}")

        thumbFir.initial(THUMBS_TAG_FIRST, 50f, h / 2f)
        thumbSec.initial(THUMBS_TAG_SECOND, w - 50f, h / 2f)
        activeBar.updateCoordinate(50f, w - 50f)
        positions = arrayOf(50f, w - 50f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Get measureSpec mode and size values.
        val measureWidthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val measureWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        var width = DEFAULT_WIDTH
        var height = DEFAULT_HEIGHT
        println("onMeasure confirm width: ${measureHeight}, height: ${measureHeight}")

        // The RangeBar width should be as large as possible.
        if (measureWidthMode == View.MeasureSpec.AT_MOST) {
            width = measureWidth
        } else if (measureWidthMode == View.MeasureSpec.EXACTLY) {
            width = measureWidth
        }

        // The RangeBar height should be as small as possible.
        if (measureHeightMode == View.MeasureSpec.AT_MOST) {
            height = Math.min(DEFAULT_HEIGHT, measureHeight)
        } else if (measureHeightMode == View.MeasureSpec.EXACTLY) {
            height = measureHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        backBar.color = Color.GRAY
        backBar.isAntiAlias = true
        backBar.style = Paint.Style.FILL

        backBarRect = RectF(50f, canvas?.height!! / 2 - 5f , canvas?.width!!.toFloat() - 50f, canvas?.height!! / 2 + 5f)

        canvas?.drawRoundRect(backBarRect, 20f, 20f, backBar)

        activeBar.draw(canvas)
        thumbFir.draw(canvas)
        thumbSec.draw(canvas)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!thumbSec.isPressed && thumbFir.isInTargetArea(event.x, event.y)) {
                        thumbFir.isPressed = true
                        positions[0] = event.x
                    } else if (!thumbFir.isPressed && thumbSec.isInTargetArea(event.x, event.y)) {
                        thumbSec.isPressed = true
                        positions[1] = event.x
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (event.x >= backBarRect.left && event.x<= backBarRect.right) {
                        if (thumbFir.isPressed) {
                            thumbFir.posX += event.x - positions[0]
                            positions[0] = event.x
                            var scaleSpan = backBarRect.width() / (max - min)
                            if ( scaleSpan * (scalePositions[0] + 1) <= event.x ) {
                                scalePositions[0] += 1
                                println("THUMBS First scale position update to : ${scalePositions[0]}")
                                if (changeListener != null) {
                                    changeListener.updateRangeSlider(scalePositions[0], scalePositions[1])
                                }
                            } else if (scalePositions[0] > 0 && scaleSpan * (scalePositions[0] - 1) >= event.x) {
                                scalePositions[0] -= 1
                                println("THUMBS First scale position update to : ${scalePositions[0]}")
                                if (changeListener != null) {
                                    changeListener.updateRangeSlider(scalePositions[0], scalePositions[1])
                                }
                            }
                        } else if (thumbSec.isPressed) {
                            thumbSec.posX += event.x - positions[1]
                            positions[1] = event.x
                            var scaleSpan = backBarRect.width() / (max - min)
                            if ( scaleSpan * (scalePositions[1] + 1) <= event.x ) {
                                scalePositions[1] += 1
                                println("THUMBS Second scale position update to : ${scalePositions[1]}")
                                if (changeListener != null) {
                                    changeListener.updateRangeSlider(scalePositions[0], scalePositions[1])
                                }
                            } else if (scalePositions[1] > 0 && scaleSpan * (scalePositions[1] - 1) >= event.x) {
                                scalePositions[1] -= 1
                                println("THUMBS Second scale position update to : ${scalePositions[1]}")
                                if (changeListener != null) {
                                    changeListener.updateRangeSlider(scalePositions[0], scalePositions[1])
                                }
                            }
                        }
                        activeBar.updateCoordinate(positions[0], positions[1])
                        invalidate()
                    }
                }
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    thumbSec.isPressed = false
                    thumbFir.isPressed = false
                }
                else -> {
                    thumbSec.isPressed = false
                    thumbFir.isPressed = false
                }
            }
        return true
    }



    interface RangeChangeListerner {
        fun updateRangeSlider(leftIndex: Int, rightIndex: Int)
    }
}