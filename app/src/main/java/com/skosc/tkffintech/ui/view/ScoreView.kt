package com.skosc.tkffintech.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.skosc.tkffintech.R
import com.skosc.tkffintech.utils.dp
import java.lang.IllegalArgumentException

private const val DEFAULT_SCORE = "0.0"

class ScoreView(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : CardView(context, attributeSet, defStyleAttr) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    private val localAttrs = context.obtainStyledAttributes(attributeSet,
            R.styleable.ScoreView,
            defStyleAttr,
            0
    )

    private val borderSize = dp(6)
    private val baseColor = localAttrs.getColor(R.styleable.ScoreView_color, Color.rgb(255, 0, 0))

    private val borderPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = borderSize
        color = baseColor
    }

    private val bgPaint = Paint().apply {
        color = Color.argb(64, Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor))
    }

    private val textPaint = Paint().apply {
        textSize = dp(textSize.toInt() * 2F)
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    var text: String = localAttrs.getString(R.styleable.ScoreView_score) ?: DEFAULT_SCORE
        get() = field
        set(value) {
            if (value.length > 3) {
                throw IllegalArgumentException("Unsupported score value, should be less than 3 characters long")
            }
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return

        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), borderPaint)

        canvas.drawRect(
                borderSize / 2,
                borderSize / 2,
                width.toFloat() - borderSize / 2,
                height.toFloat() - borderSize / 2,
                bgPaint)

        val xPos = width / 2
        val yPos = (height / 2 - (textPaint.descent() + textPaint.ascent()) / 2)

        canvas.drawText(text, xPos.toFloat(), yPos, textPaint)

        super.onDraw(canvas)
    }
}