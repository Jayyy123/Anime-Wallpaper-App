package com.example.animeimages

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class RecyclerViewClicker(private val recycleView:RecyclerView, private val context: Context, private val listener:onRecycleClickListener ): RecyclerView.SimpleOnItemTouchListener() {
    private val TAG = ""
    interface onRecycleClickListener{
        fun onShortClick(view: View, position: Int)
        fun onLongClick(view: View, position: Int)
    }

    private val gesture = GestureDetectorCompat(context,object :GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent): Boolean {

            val childView = recycleView.findChildViewUnder(e.x,e.y)
            Log.d(TAG, "shortview startsssssss")
            if (childView != null) {
                listener.onShortClick(childView,recycleView.getChildAdapterPosition(childView))
            }
            return true
        }

        override fun onLongPress(e: MotionEvent) {

            val childView = recycleView.findChildViewUnder(e.x,e.y)
            Log.d(TAG, "long startsssssss")
            listener.onLongClick(childView!!,recycleView.getChildAdapterPosition(childView))
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//        return super.onInterceptTouchEvent(rv, e)
        val result = gesture.onTouchEvent(e)
        return result
    }
}