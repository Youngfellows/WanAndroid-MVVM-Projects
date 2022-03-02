package com.longjunhao.wanjetpack.api

import com.google.gson.annotations.SerializedName

/**
 * .WanJetResponse
 *  todo WanJetResponse要用ApiResponse代替
 * @author Admitor
 * @date 2021/06/08
 */
data class WanJetResponse<T>(
    @field:SerializedName("data") val data: T,
    @field:SerializedName("errorCode") val errorCode: Int,
    @field:SerializedName("errorMsg") val errorMsg: String
)
