package xyz.kuririnz.rangeslider

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import xyz.kuririnz.rangesliderview.RangeSliderView

class MainActivity : AppCompatActivity()
        , View.OnTouchListener, RangeSliderView.RangeChangeListerner {
    lateinit var textview: TextView
    lateinit var rangeSliderView: RangeSliderView
    var position = arrayListOf<Float>(0f, 0f)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textview = findViewById(R.id.ExampleText)
        rangeSliderView = findViewById(R.id.rangeSliderView)

        rangeSliderView.changeListener = this
        textview.setOnTouchListener(this)
        
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            position[0] = event.rawX
            position[1] = event.rawY
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            v!!.x += event.rawX - position[0]
            v!!.y += event.rawY - position[1]
            position[0] = event.rawX
            position[1] = event.rawY
        } else if (event.action == MotionEvent.ACTION_CANCEL && event.action == MotionEvent.ACTION_UP) {

        }
        return true
    }

    override fun updateRangeSlider(leftIndex: Int, rightIndex: Int) {
        textview.text = "left index: ${leftIndex}, right index: ${rightIndex}"
    }
}
