package com.jupiter.application.handpaint

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.jupiter.application.handpaint.ml.Shapes
import kotlinx.android.synthetic.main.activity_main.*
import org.tensorflow.lite.support.image.TensorImage


class MainActivity : AppCompatActivity() {
    fun classifyDrawing(bitmap : Bitmap) {
        val model = Shapes.newInstance(this)

        // Creates inputs for reference.
        val image = TensorImage.fromBitmap(bitmap)

        // Runs model inference and gets result.
      //  val outputs = model.process(image)
      //  val probability = outputs.probabilityAsCategoryList

        val outputs = model.process(image).probabilityAsCategoryList.apply{
            sortByDescending { it.score }
        }.take(2)
        var Result:String =""
        for(output in outputs) {
            when (output.label) {
                "circle" -> Result += "Round Shape"
                "square" -> Result += "Square Shape"
                "star" -> Result += "Star Shape"
                "triangle" -> Result += "Triangle Shape"
            }
            Result += ": " + String.format("%.1f%%", output.score * 100.0f)
        }
        // Releases model resources if no longer used.
        model.close()
        Toast.makeText(this, Result.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                handy.path.reset()
                handy.invalidate()
            }
        })

        handy.setOnTouchListener(object:View.OnTouchListener{
            override fun onTouch(p0: View?,event: MotionEvent): Boolean {
                var xPos = event.getX()
                var yPos = event.getY()
                when(event.action){
                    MotionEvent.ACTION_DOWN -> handy.path.moveTo(xPos,yPos)
                    MotionEvent.ACTION_MOVE -> handy.path.lineTo(xPos,yPos)
                    MotionEvent.ACTION_UP   -> {
                        val b = Bitmap.createBitmap(handy.measuredWidth, handy.measuredHeight,
                            Bitmap.Config.ARGB_8888)
                        val c = Canvas(b)
                        handy.draw(c)
                        classifyDrawing(b)
                    }
                }
                handy.invalidate()
                return true
            }
        })


    }
}