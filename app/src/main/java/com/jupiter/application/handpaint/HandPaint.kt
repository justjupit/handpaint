package com.jupiter.application.handpaint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class HandPaint(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var path: Path = Path()

    init {
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 80f
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)
        //canvas.drawRect(200f,200f,400f,600f,paint)
        //path.moveTo(500f,800f)
        //path.lineTo(500f,1200f)
        //path.lineTo(700f,1000f)
        canvas.drawPath(path, paint)

    }
}
/*


    override fun onTouchEvent(event: MotionEvent): Boolean {
        var xPos = event.getX()
        var yPos = event.getY()
        when(event.action){
            MotionEvent.ACTION_DOWN -> path.moveTo(xPos,yPos)
            MotionEvent.ACTION_MOVE -> path.lineTo(xPos,yPos)
        }
        invalidate()
        return true
    }
}

*/