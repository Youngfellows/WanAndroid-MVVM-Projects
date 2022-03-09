package com.bbq.net.model

import com.bbq.net.exception.ResultException

/**
 * 返回的基础model
 * @param T 泛型数据
 */
class BaseResult<T> {

    /**
     * 错误码0为正确
     */
    var errorCode: Int = -1

    /**
     * 错误消息
     */
    var errorMsg: String? = null

    /**
     * 返回数据,泛型
     */
    var data: T? = null
        private set

    /**
     * 数据状态
     */
    var dataStatus: DataStatus? = null

    /**
     * 当STATE_ERROR状态的时候这个才会有值
     */
    var exception: ResultException? = null
}