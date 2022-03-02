package com.bbq.net.model

/**
 * 数据状态
 */
enum class DataStatus {
    STATE_LOADING,//加载中
    STATE_SUCCESS,//成功
    STATE_ERROR,//1.服务器处理后的失败  2.网络等原因导致的失败
}