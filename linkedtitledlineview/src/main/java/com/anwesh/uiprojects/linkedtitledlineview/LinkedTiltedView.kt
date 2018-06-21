package com.anwesh.uiprojects.linkedtitledlineview

/**
 * Created by anweshmishra on 21/06/18.
 */

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.MotionEvent

val LTL_NODES : Int = 5

class LinkedTiltedView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class LTState(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(stopcb : (Float) -> Unit) {
            scale += 0.1f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(prevScale)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class LTAnimator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class LTLNode(var i : Int, val state : LTState = LTState()) {

        private var next : LTLNode? = null

        private var prev : LTLNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < LTL_NODES - 1) {
                next = LTLNode(i)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val gap : Float = (w / LTL_NODES)
            paint.strokeWidth = Math.min(w, h) / 60
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.parseColor("#2980b9")
            canvas.save()
            canvas.translate(0f + (gap/2) * i, h - i * (gap * Math.sqrt(3.0)).toFloat()/2)
            canvas.rotate(30f + 180f * state.scale)
            canvas.drawLine(0f, 0f, 0f, gap, paint)
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : LTLNode {
            var curr : LTLNode? = next
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }
}