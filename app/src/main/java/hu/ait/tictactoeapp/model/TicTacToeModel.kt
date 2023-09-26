package hu.ait.tictactoeapp.model

import hu.ait.tictactoeapp.MainActivity
import android.content.Context
import kotlin.math.max

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.tictactoeapp.model.TicTacToeModel


object TicTacToeModel {
    public val EMPTY: Short = 0
    public val CIRCLE: Short = 1
    public val CROSS: Short = 2

    private val model = arrayOf(
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY))

    private var nextPlayer = CIRCLE
    private var turnNumber = 0

    fun resetModel() {
        for (i in 0..2) {
            for (j in 0..2) {
                model[i][j] = EMPTY
            }
        }
        nextPlayer = CIRCLE
        turnNumber = 0
    }

    fun getFieldContent(x: Int, y: Int) = model[x][y]

    fun setFieldContent(x: Int, y: Int, content: Short) {
        model[x][y] = content
    }

    fun getNextPlayer() = nextPlayer

    fun changeNextPlayer() {
        nextPlayer = if (nextPlayer == CIRCLE) CROSS else CIRCLE
        turnNumber++
    }

    fun checkWin(): Short {
        var winner = max(max(checkDiagonals().toInt(), checkVertical().toInt()), checkHorizontal().toInt()).toShort()
        if(turnNumber == 9 && winner < EMPTY)  {
            return -2
        }
        return winner
    }

    fun checkDiagonals(): Short {
        if(model[0][0] == model[1][1] && model[1][1] == model[2][2] && model[0][0] != EMPTY || model[0][2] != EMPTY &&
            model[0][2] == model[1][1] && model[2][0] == model[1][1]){
            return model[1][1]
        }
        return -1
    }

    fun checkVertical(): Short {
        for (i in 0..2) {
            if(model[0][i] == model[1][i] && model[1][i] == model[2][i] && model[0][i] != EMPTY) {
                return model[0][i]
            }
        }
        return -1
    }

    fun checkHorizontal(): Short {
        for (i in 0..2) {
            if(model[i][0] == model[i][1] && model[i][1] == model[i][2] && model[i][0] != EMPTY) {
                return model[i][0]
            }
        }
        return -1
    }

}