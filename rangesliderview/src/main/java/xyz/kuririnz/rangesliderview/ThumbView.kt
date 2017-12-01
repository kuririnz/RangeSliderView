package xyz.kuririnz.rangesliderview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.sql.DriverManager.println

/**
 * RangeSliderView Thumb View
 */
class ThumbView(context: Context
                   , attributeSet: AttributeSet? = null
                   , defStyleAttr: Int = 0) : View(context, attributeSet, defStyleAttr) {

    private val THUMBS_MIN_SIZE = 50
    private val THUMBS_DEFAULT_RUDIAS = 25f

    private val c = context
    private var thumbPaint = Paint()
    var posX : Float = 0f
    var posY : Float = 0f

    /*====================================================
    // Secondary Constructor
    ====================================================*/
    constructor(context: Context, attributeSet: AttributeSet): this(context, attributeSet, 0) {
        this.isFocusableInTouchMode = true
    }

    constructor(context: Context): this(context, null)

    /*====================================================
    // Life Cycle Methods
    ====================================================*/
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        println("onSizeChanged confirm w: ${w}, h: ${h}, old data -> ${oldw} : ${oldh}")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Get measureSpec mode and size values.
        val measureWidthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val measureHeightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val measureWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        var width = THUMBS_MIN_SIZE
        var height = THUMBS_MIN_SIZE
        println("onMeasure confirm width: ${measureHeight}, height: ${measureHeight}")

        // The RangeBar width should be as large as possible.
        if (measureWidthMode == View.MeasureSpec.AT_MOST) {
            width = measureWidth
        } else if (measureWidthMode == View.MeasureSpec.EXACTLY) {
            width = measureWidth
        }

        // The RangeBar height should be as small as possible.
        if (measureHeightMode == View.MeasureSpec.AT_MOST) {
            height = Math.min(THUMBS_MIN_SIZE, measureHeight)
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
        thumbPaint.style = Paint.Style.FILL_AND_STROKE
        thumbPaint.color = Color.CYAN
        thumbPaint.strokeWidth = 2f
        thumbPaint.isAntiAlias = true

        canvas?.drawCircle(posX, canvas?.height / 2f , THUMBS_DEFAULT_RUDIAS, thumbPaint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    /*====================================================
    // Public Methods
    ====================================================*/
    fun initial(thumbsTag: String, xPosition: Float, yPosition: Float) {
        this.tag = thumbsTag
        this.posX = xPosition
        this.posY = yPosition
    }

    fun isInTargetArea (x :Float, y :Float) :Boolean {
        return (Math.abs(x - posX) <= THUMBS_DEFAULT_RUDIAS && Math.abs(y - posY) <= THUMBS_DEFAULT_RUDIAS);
    }
}