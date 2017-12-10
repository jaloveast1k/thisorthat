package com.jaloveast1k.thisorthat.view.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class Diagramm(arg0: Context, arg1: AttributeSet) : View(arg0, arg1) {
    private val paintUp: Paint
    private val paintDown: Paint

    private var percentDown: Int = 0
    private var oval: RectF? = null
    private var landscape: Boolean = false

    init {

        paintUp = Paint()
        paintUp.isAntiAlias = true
        paintUp.color = Color.rgb(0xd5, 0x4b, 0x33)

        paintDown = Paint()
        paintDown.isAntiAlias = true
        paintDown.color = Color.rgb(0x5f, 0x48, 0x86)

        setUpPercents(75)
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var viewSize = if (landscape) {
            View.MeasureSpec.getSize(widthMeasureSpec)
        } else {
            View.MeasureSpec.getSize(heightMeasureSpec)
        }

        oval = RectF(0f, 0f, viewSize.toFloat(), viewSize.toFloat())

        setMeasuredDimension(viewSize, viewSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val sweepAngle = 360 * percentDown / 100
        val halfOfDiff = (180 - sweepAngle) / 2

        if (landscape) {
            canvas.drawArc(oval!!, (halfOfDiff - 90).toFloat(), (-(360 - sweepAngle)).toFloat(), true, paintUp)
            canvas.drawArc(oval!!, (halfOfDiff - 90).toFloat(), sweepAngle.toFloat(), true, paintDown)
        } else {
            canvas.drawArc(oval!!, halfOfDiff.toFloat(), (-(360 - sweepAngle)).toFloat(), true, paintUp)
            canvas.drawArc(oval!!, halfOfDiff.toFloat(), sweepAngle.toFloat(), true, paintDown)
        }
    }

    fun setUpPercents(percentDown: Int) {
        this.percentDown = percentDown

        invalidate()
    }

    fun setOrientation(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            landscape = true
        } else {
            landscape = false
        }
    }
}
