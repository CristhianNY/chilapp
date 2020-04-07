package com.cristhianbonilla.com.artistasamerica.ui.customviews

import android.graphics.RectF

interface SizeTester {

    public fun onTestSize(suggestedSize:Int, rect:RectF):Int
}