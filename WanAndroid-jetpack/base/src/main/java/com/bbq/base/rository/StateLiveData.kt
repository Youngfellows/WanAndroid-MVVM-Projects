package com.bbq.base.rository

import androidx.lifecycle.MutableLiveData
import com.bbq.net.model.BaseResult

/**
 * 被观察数据
 * @param T 泛型数据,封装到BaseResult
 */
class StateLiveData<T> : MutableLiveData<BaseResult<T>>() {

}