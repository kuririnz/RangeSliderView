package xyz.kuririnz.rangesliderview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import java.sql.DriverManager.println

/**
 * Created by keisuke_kuribayashi on 2017/11/30.
 */
class ActiveBarView(context: Context
                    , attributeSet: AttributeSet? = null
                    , defStyleAttr: Int = 0) : View(context, attributeSet, defStyleAttr)  {

    private val DEFAULT_WIDTH = 400
    private val DEFAULT_HEIGHT = 10

    private var activeBar = Paint()

    private var leftX = 0f
    private var rightX = 0f


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
        activeBar.color = Color.CYAN
        activeBar.isAntiAlias = true
        activeBar.style = Paint.Style.FILL

        val rectF = RectF(leftX, canvas?.height!! / 2 - 5f , rightX, canvas?.height!! / 2 + 5f)

        canvas?.drawRoundRect(rectF, 20f, 20f, activeBar)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    /*====================================================
    // Public Methods
    ====================================================*/
    fun updateCoordinate(xPosition: Float, yPosition: Float) {
        this.leftX = xPosition
        this.rightX = yPosition
    }

}