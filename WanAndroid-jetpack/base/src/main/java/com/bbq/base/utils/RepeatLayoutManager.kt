package com.bbq.base.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * 一款可以让RecyclerView无限循环的LayoutManager
 * 修改自jiarWang的RepeatLayoutManager
 * https://github.com/jiarWang/RepeatLayoutManager
 */
class RepeatLayoutManager(val context: Context) : LinearLayoutManager(context) {

    /**
     * 水平滚动
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        fillHorizontal(dx, recycler)
        offsetChildrenHorizontal(-dx)
        recyclerChildView(dx > 0, recycler)
        return dx
    }

    /**
     * 垂直滚动
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        fillVertical(dy, recycler)
        offsetChildrenVertical(-dy)
        recyclerChildView(dy > 0, recycler)
        return dy
    }

    /**
     * 水平填充
     * @param dx
     * @param recycler
     */
    private fun fillHorizontal(dx: Int, recycler: RecyclerView.Recycler) {
        if (childCount == 0) return
        if (dx > 0) {
            getChildAt(childCount - 1)?.let {
                var anchorView = it
                val anchorPosition = getPosition(anchorView)
                while (anchorView.right < width - paddingRight + dx) {
                    var position = (anchorPosition + 1) % itemCount
                    if (position < 0) position += itemCount
                    val scrapItem = recycler.getViewForPosition(position)
                    addView(scrapItem)
                    measureChildWithMargins(scrapItem, 0, 0)
                    val left = anchorView.right
                    val top = paddingTop
                    val right = left + getDecoratedMeasuredWidth(scrapItem)
                    val bottom = top + getDecoratedMeasuredHeight(scrapItem)
                    layoutDecorated(scrapItem, left, top, right, bottom)
                    anchorView = scrapItem
                }
            }
        } else {
            getChildAt(0)?.let {
                var anchorView = it
                val anchorPosition = getPosition(anchorView)
                while (anchorView.left > paddingLeft + dx) {
                    var position = (anchorPosition - 1) % itemCount
                    if (position < 0) position += itemCount
                    val scrapItem = recycler.getViewForPosition(position)
                    addView(scrapItem, 0)
                    measureChildWithMargins(scrapItem, 0, 0)
                    val right = anchorView.left
                    val top = paddingTop
                    val left = right - getDecoratedMeasuredWidth(scrapItem)
                    val bottom = top + getDecoratedMeasuredHeight(scrapItem)
                    layoutDecorated(
                        scrapItem, left, top,
                        right, bottom
                    )
                    anchorView = scrapItem
                }
            }
        }
        return
    }

    /**
     * 垂直填充
     * @param dy
     * @param recycler
     */
    private fun fillVertical(dy: Int, recycler: RecyclerView.Recycler) {
        if (childCount == 0) return
        if (dy > 0) {
            //填充尾部
            getChildAt(childCount - 1)?.let {
                var anchorView = it //最后一个View视图,锚点
                val anchorPosition = getPosition(anchorView) //最后一个锚点View视图,位置
                while (anchorView.bottom < height - paddingBottom + dy) {
                    var position = (anchorPosition + 1) % itemCount
                    if (position < 0) position += itemCount
                    val scrapItem = recycler.getViewForPosition(position)
                    addView(scrapItem)
                    measureChildWithMargins(scrapItem, 0, 0)
                    val left = paddingLeft
                    val top = anchorView.bottom
                    val right = left + getDecoratedMeasuredWidth(scrapItem)
                    val bottom = top + getDecoratedMeasuredHeight(scrapItem)
                    layoutDecorated(scrapItem, left, top, right, bottom)
                    anchorView = scrapItem
                }
            }
        } else {
            //填充头部
            getChildAt(0)?.let {
                var anchorView = it
                val anchorPosition = getPosition(anchorView)
                while (anchorView.top > paddingTop + dy) {
                    var position = (anchorPosition - 1) % itemCount
                    if (position < 0) position += itemCount
                    val scrapItem = recycler.getViewForPosition(position)
                    addView(scrapItem, 0)
                    measureChildWithMargins(scrapItem, 0, 0)
                    val left = paddingLeft
                    val right = left + getDecoratedMeasuredWidth(scrapItem)
                    val bottom = anchorView.top
                    val top = bottom - getDecoratedMeasuredHeight(scrapItem)
                    layoutDecorated(
                        scrapItem, left, top,
                        right, bottom
                    )
                    anchorView = scrapItem
                }
            }
        }
        return
    }

    /**
     * 回收界面不可见的view
     */
    private fun recyclerChildView(
        fillEnd: Boolean,
        recycler: RecyclerView.Recycler
    ) {
        if (fillEnd) {
            //回收头部
            for (i in 0 until childCount) {
                val view = getChildAt(i) ?: return
                val needRecycler = if (orientation == RecyclerView.HORIZONTAL)
                    view.right < paddingLeft
                else
                    view.bottom < paddingTop
                if (!needRecycler) return
                removeAndRecycleView(view, recycler)
            }
        } else {
            //回收尾部
            for (i in childCount - 1 downTo 0) {
                val view = getChildAt(i) ?: return
                val needRecycler = if (orientation == RecyclerView.HORIZONTAL)
                    view.left > width - paddingRight
                else
                    view.top > height - paddingBottom
                if (!needRecycler) return
                removeAndRecycleView(view, recycler)
            }
        }
    }
}