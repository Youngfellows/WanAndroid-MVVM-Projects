package com.bbq.net.exception

/**
 * 异常结果类
 * @property errCode 错误码
 * @property msg 错误信息
 */
class ResultException(var errCode: String, var msg: String) : Exception(msg)
