package com.bbq.base.utils

import androidx.lifecycle.MutableLiveData

/**
 * 事件总线,单例的
 */
object LiveDataBus {

    /**
     * 被观察的数据Map列表
     */
    private val mMapAll = HashMap<String, Any>()

    /**
     * 获取被观察的数据
     * @param T 泛型数据
     * @param key 对应的Key
     * @return
     */
    fun <T> with(key: String): MutableLiveData<T> {
        if (!mMapAll.containsKey(key)) {
            mMapAll[key] = MutableLiveData<T>()
        }
        return (mMapAll[key] as MutableLiveData<T>)
    }

}