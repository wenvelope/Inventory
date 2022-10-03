package com.example.inventory.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FunItemDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if(position in 0..2){
            outRect.set(0,200,0,0)
        }else if(position in 5..6){
            outRect.set(0,200,0,0)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val paint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
            textSize=70f
        }
        val count = parent.childCount
        for(i in 0..count){
            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)
            if(position == 5){
                c.drawText("仓库管理",40f,view.top-70f,paint)
            }
            if(position == 0 ){
                c.drawText("功能",40f,view.top-70f,paint)
            }
        }
    }

}