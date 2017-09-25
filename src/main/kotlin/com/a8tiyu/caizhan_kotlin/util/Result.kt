package com.a8tiyu.caizhan_kotlin.util

data class Result<T>(
        var code: Int? = -1,
        var errorMessage: String? = "error",
        var data: T
)

