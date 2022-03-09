package com.bbq.base.utils

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * 自动轮播得RecyclerView帮助
 * @property recyclerView RV列表视图
 * @property orientation 方向
 */
class SimpleBannerHelper(
    private val recyclerView: RecyclerView,
    @RecyclerView.Orientation
    private val orientation: Int = RecyclerView.HORIZONTAL
) {

    /**
     * 一款可以让RecyclerView无限循环的LayoutManager
     */
    private var repeatLayoutManager = RepeatLayoutManager(recyclerView.context)

    /**
     * 平滑滚动时间
     */
    private var smoothScrollDuration = 500

    /**
     * 延迟时间
     */
    private var bannerDelay = 5000L

    /**
     * X方向偏移
     */
    private var offsetX = 0

    /**
     * Y方向偏移
     */
    private var offsetY = 0

    /**
     * 是否正在拖拽
     */
    private var isUp = false

    /**
     * 是否正在滚动状态
     */
    private var isSettling = false

    /**
     * 开启无限循环任务
     */
    private val timerTask = Runnable {
        recyclerView.post {
            if (repeatLayoutManager.itemCount > 1) {
                val position = repeatLayoutManager.childCount - 1
                if (orientation == RecyclerView.VERTICAL) {
                    repeatLayoutManager.getChildAt(position)?.let { view ->
                        val dy = view.height
                        //上下自动滚动
                        recyclerView.smoothScrollBy(0, dy, null, smoothScrollDuration)
                    }
                } else if (orientation == RecyclerView.HORIZONTAL) {
                    repeatLayoutManager.getChildAt(position)?.let { view ->
                        val dx = view.width
                        //水平自动滚动
                        recyclerView.smoothScrollBy(dx, 0, null, smoothScrollDuration)
                    }
                }
                startTimerTask()
            }
        }
    }

    init {
        repeatLayoutManager.orientation = orientation
        recyclerView.layoutManager = repeatLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //RV闲置状态
                    isSettling = false
                    if (isUp) {
                        isUp = false
                        offsetItem()
                        startTimerTask()
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //拖拽RV
                    isUp = true
                    if (!isSettling) {
                        offsetX = 0
                        offsetY = 0
                    }
                    stopTimerTask()
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    //滚动状态
                    isSettling = true
                    stopTimerTask()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                offsetX += dx
                offsetY += dy
            }
        })
    }

    /**
     * 从置偏移项
     */
    private fun offsetItem() {
        val position = repeatLayoutManager.childCount - 1
        if (orientation == RecyclerView.VERTICAL) {
            repeatLayoutManager.getChildAt(position)?.let { view ->
                val height = view.height
                val offset = offsetY % height
                //上下滚动
                if (abs(offset) >= height / 2) {
                    if (offset >= 0) {
                        recyclerView.smoothScrollBy(0, height - offset)
                    } else {
                        recyclerView.smoothScrollBy(0, abs(offset) - height)
                    }
                } else {
                    recyclerView.smoothScrollBy(0, -offset)
                }
            }
        } else if (orientation == RecyclerView.HORIZONTAL) {
            repeatLayoutManager.getChildAt(position)?.let { view ->
                val width = view.width
                val offset = offsetX % width
                //水平滚动
                if (abs(offset) >= width / 2) {
                    if (offset >= 0) {
                        recyclerView.smoothScrollBy(width - offset, 0)
                    } else {
                        recyclerView.smoothScrollBy(abs(offset) - width, 0)
                    }
                } else {
                    recyclerView.smoothScrollBy(-offset, 0)
                }
            }
        }
    }

    /**
     * 返回最后一个可见条目位置
     * @return
     */
    fun findLastVisibleItemPosition(): Int {
        return repeatLayoutManager.findLastVisibleItemPosition()
    }

    /**
     * 启动任务
     */
    fun startTimerTask() {
        recyclerView.postDelayed(timerTask, bannerDelay)
    }

    /**
     * 停止任务
     */
    fun stopTimerTask() {
        recyclerView.removeCallbacks(timerTask)
    }

}

