package com.cristhianbonilla.com.chilapp.ui.customviews

import android.R.attr
import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.graphics.RectF
import android.graphics.Typeface
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.method.ScrollingMovementMethod
import android.util.AttributeSet
import android.util.Log
import android.util.SparseIntArray
import android.util.TypedValue
import android.view.View
import android.widget.Scroller
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import kotlin.properties.Delegates


class ResizeEditText : TextInputEditText {
    private val NO_LINE_LIMIT = -1
    private val availableSpaceRect = RectF()
    var maxline:Int = 0
    private var minTextSize:Float = 0.0f
    private var maxTextSize:Float = 0.0f
    lateinit var sizeTester:SizeTester
    var  textPaint: TextPaint? = null
    var widthLimit by Delegates.notNull<Int>()
    private var spacingMult = 1.0f
    private var spacingAdd = 0.0f
    private var enableSizeCache = true
    private var initiallized = false
    private var miniSizeText:Int = 70
    private val textCachedSizes = SparseIntArray()
    constructor(context : Context?) : super(context)
    constructor(context : Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
    }
    constructor(context : Context, attrs : AttributeSet?, defStyleAttr : Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
    }

    init{

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initAttrs(context : Context, attrs: AttributeSet?) {

        minTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6F,resources.displayMetrics)
        maxTextSize = textSize

        focusable = View.FOCUSABLE

       if( maxline == 0){
           maxline = NO_LINE_LIMIT

          sizeTester = object : SizeTester{
              val textRectF = RectF()

              @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
              override fun onTestSize(suggestedSize: Int, availablespace: RectF): Int {
                  textPaint?.textSize = suggestedSize.toFloat()
                  val text = text.toString()
                  val singleLine:Boolean = maxLines ==1

                  if(singleLine){
                      textRectF.bottom = paint.fontSpacing
                      textRectF.right = paint.measureText(text)
                  }else {

                      val layout = StaticLayout(
                          text,
                          textPaint,
                          widthLimit,
                          Layout.Alignment.ALIGN_NORMAL,
                          spacingMult,
                          spacingAdd,
                          true
                      )
                      Log.d("NLN", "Current Lines = " + Integer.toString(layout.lineCount));
                      Log.d("NLN", "Max Lines = " + Integer.toString(maxLines));

                      if (maxline != NO_LINE_LIMIT
                          && layout.lineCount > maxLines
                      ){
                          return 1
                      }

                      textRectF.bottom = layout.height.toFloat()
                      var maxWidth = -1

                      for (i in 0 downTo  lineCount){
                          if (maxWidth < layout.getLineWidth(i)){
                              maxWidth = layout.getLineWidth(i).toInt()
                          }
                      }
                      textRectF.right = maxWidth.toFloat()

                  }
                  textRectF.offsetTo(0F, 0F)
                  return if(availablespace.contains(textRectF)){
                      -1
                  }else{
                      1
                  }


              }
          }
           initiallized = true
       }
    }

    override fun setTypeface(tf: Typeface?) {
        if(textPaint == null)
            textPaint = TextPaint(paint)
        textPaint?.typeface = tf
        super.setTypeface(tf)
    }

    override fun setTextSize(size: Float) {
        maxTextSize = size
        textCachedSizes.clear()
        adjustTextSize();
    }

    override fun setMaxLines(maxLines: Int) {
        super.setMaxLines(maxLines)
        maxline = maxLines
        reAdjust();
    }

    fun setEnableSizeCache(enable: Boolean) {
        enableSizeCache = enable
        textCachedSizes.clear()
        adjustTextSize()
    }

    override fun setTextSize(unit: Int, size: Float) {
        val c = context
        val r: Resources
        r = if (c == null) Resources.getSystem() else c.resources
        maxTextSize = TypedValue.applyDimension(
            unit, size,
            r.displayMetrics
        )
        textCachedSizes.clear()
        adjustTextSize()
    }


    override fun setLineSpacing(add: Float, mult: Float) {
        super.setLineSpacing(add, mult)
        spacingMult = mult
        spacingAdd = add
    }

     fun setMinimumTextSize(minTextSize: Float) {
        this.minTextSize = minTextSize
        reAdjust()
    }


    private  fun reAdjust() {
        adjustTextSize()
    }
    private  fun adjustTextSize() {
        if (!initiallized) return
        val startSize = minTextSize.toInt()
        val heightLimit = (measuredHeight
                - compoundPaddingBottom - compoundPaddingTop)
        widthLimit = (measuredWidth - compoundPaddingLeft
                - compoundPaddingRight)
        if (widthLimit <= 0) return
        availableSpaceRect.right = widthLimit.toFloat()
        availableSpaceRect.bottom = heightLimit.toFloat()

        super.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            efficientTextSizeSearch(
                startSize as Int , maxTextSize.toInt(),
                sizeTester, availableSpaceRect
            ).toFloat()
        )
    }

    private fun efficientTextSizeSearch(
        start: Int, end: Int,
        sizeTester: SizeTester, availableSpace: RectF
    ): Int {
      //  if (!enableSizeCache) return customBinarySearch(start, end, sizeTester, availableSpace)
        val text = text.toString()
        val key = text?.length ?: 0
        var size: Int = textCachedSizes.get(key)

        if(key >=219){
            return miniSizeText
        }
        if (size != 0) {
            return size
        }

        size = customBinarySearch(start, end, sizeTester, availableSpace)
        textCachedSizes.put(key, size)
        return size
    }

    private fun customBinarySearch(start: Int,end: Int,sizeTester: SizeTester,availableSpace: RectF) :Int{
        var lastBest = start
        var lo = start
        var hi = attr.end - 1
        var mid = 0
        while (lo <= hi) {
            mid = lo + hi ushr 1
            val midValCmp = sizeTester.onTestSize(mid, availableSpace)
            if (midValCmp < 0) {
                lastBest = lo
                lo = mid + 1
            } else if (midValCmp > 0) {
                hi = mid - 1
                lastBest = hi
            } else return mid
        }
        // make sure to return last best
        // this is what should always be returned
        // make sure to return last best
        // this is what should always be returned
        return lastBest
    }

    override fun getMaxLines(): Int {
        return maxline
    }

    override fun setSingleLine() {
        super.setSingleLine()
        maxline = 1
        reAdjust()
    }

    override fun setSingleLine(singleLine: Boolean) {
        super.setSingleLine(singleLine)
        if (singleLine) maxline = 1 else maxline = NO_LINE_LIMIT
        reAdjust()
    }
    override fun setLines(lines: Int) {
        super.setLines(lines)
        maxline = lines
        reAdjust()
    }

    override fun onTextChanged(
        text: CharSequence?, start: Int,
        before: Int, after: Int
    ) {
        super.onTextChanged(text, start, before, after)
        reAdjust()
    }

    override fun onSizeChanged(
        width: Int, height: Int,
        oldwidth: Int, oldheight: Int
    ) {
        textCachedSizes.clear()
        super.onSizeChanged(width, height, oldwidth, oldheight)
        if (width != oldwidth || height != oldheight) reAdjust()
    }
}