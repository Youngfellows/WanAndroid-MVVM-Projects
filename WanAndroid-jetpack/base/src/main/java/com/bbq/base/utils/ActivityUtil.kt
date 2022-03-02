package com.bbq.base.utils

import android.app.Activity
import java.lang.IllegalArgumentException

/**
 * Activity工具类
 */
object ActivityUtil {

    /**
     * 保存Activity的列表
     */
    var mActivities = arrayListOf<Activity>()

    /**
     * 添加Activity到列表
     * @param activity
     */
    fun addActivity(activity: Activity) {
        if (!mActivities.contains(activity)) {
            mActivities.add(activity)
        }
    }

    /**
     * 从列表移除Activity
     * @param activity
     */
    fun removeActivity(activity: Activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity)
        }
    }

    /**
     * 获取栈顶的activity
     * @return
     */
    fun getStackTopAct(): Activity {
        if (mActivities.isEmpty()) {
            throw  IllegalArgumentException("can't get Activity ")
        }
        return mActivities[mActivities.size - 1]
    }

    /**
     * 销毁全部的activity
     */
    fun finishAllActivity() {
        mActivities.forEach {
            if (!it.isFinishing) {
                it.finish()
            }
        }
    }

}