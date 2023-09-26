package hu.ait.tictactoeapp.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.tictactoeapp.MainActivity
import hu.ait.tictactoeapp.R
import hu.ait.tictactoeapp.model.TicTacToeModel

//class name capitalized but not package name
class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground = Paint()
    private var paintLine = Paint()
    private var paintCircle = Paint()
    private var paintCross = Paint()

    private var paintText = Paint()

    private var lock = false

    private var bitmapImg = BitmapFactory.decodeResource(resources, R.drawable.skateboardd)


    init{
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        paintCircle.color = Color.CYAN
        paintCircle.style = Paint.Style.STROKE
        paintCircle.strokeWidth = 5f

        paintCross.color = Color.MAGENTA
        paintCross.style = Paint.Style.STROKE
        paintCross.strokeWidth = 5f

        paintText.color = Color.GREEN
        paintText.textSize = 100f

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = h / 3f //can use gettextbounds to center

        bitmapImg = Bitmap.createScaledBitmap(bitmapImg, w, h, false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //? denotes skip if null
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        canvas?.drawText("Test", 0f, height/3f, paintText)

        canvas?.drawBitmap(bitmapImg, 0f, 0f, null)

        drawGameArea(canvas)

        drawPlayers(canvas)
    }

    private fun drawPlayers(canvas: Canvas?) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + height / 6).toFloat()
                    val radius = height / 6 - 2

                    canvas?.drawCircle(centerX, centerY, radius.toFloat(), paintCircle)
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas?.drawLine((i * width / 3).toFloat(), (j * height / 3).toFloat(),
                        ((i + 1) * width / 3).toFloat(),
                        ((j + 1) * height / 3).toFloat(), paintCross)

                    canvas?.drawLine(((i + 1) * width / 3).toFloat(), (j * height / 3).toFloat(),
                        (i * width / 3).toFloat(), ((j + 1) * height / 3).toFloat(), paintCross)
                }
            }
        }
    }

    private fun drawGameArea(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        canvas?.drawLine(0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(),
            paintLine)
        canvas?.drawLine(0f, (2 * height / 3).toFloat(), width.toFloat(),
            (2 * height / 3).toFloat(), paintLine)

        // two vertical lines
        canvas?.drawLine((width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(),
            paintLine)
        canvas?.drawLine((2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(),
            paintLine)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / 3)
            val tY = event.y.toInt() / (height / 3)

            if (TicTacToeModel.getFieldContent(tX, tY) == TicTacToeModel.EMPTY && !lock) {
                TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.getNextPlayer())
                (context as MainActivity).resetTimer()
                TicTacToeModel.changeNextPlayer()
                updateText()
                invalidate()
            }
        }
        return true
    }

    fun updateText() {
        var nextText = "Next player is 0"
        if(TicTacToeModel.getNextPlayer() == TicTacToeModel.CROSS) {
            nextText = "Next player is X"
        }
        var winner = TicTacToeModel.checkWin()
        if(winner > TicTacToeModel.EMPTY) {
            nextText = "Player $winner wins!"
            lock = true
            (context as MainActivity).stopTimer()
        } else if(winner.toInt() == -2) {
            nextText = "Tie!"
            (context as MainActivity).stopTimer()
        }
        (context as MainActivity).setMessage(nextText)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    fun resetGame() {
        TicTacToeModel.resetModel()
        updateText()
        lock = false
        (context as MainActivity).resetTimer()
        invalidate()
    }
}