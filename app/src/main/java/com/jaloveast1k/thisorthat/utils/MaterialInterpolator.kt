package com.jaloveast1k.thisorthat.utils

import android.view.animation.Interpolator

class MaterialInterpolator : Interpolator {
    override fun getInterpolation(x: Float): Float {
        return (6 * Math.pow(x.toDouble(), 2.0) - 8 * Math.pow(x.toDouble(), 3.0) + 3 * Math.pow(x.toDouble(), 4.0)).toFloat()
    }
}