package com.a8tiyu.caizhan_kotlin.template

data class Result<T>(
        var code: Int = 0,
        var errorMessage: String? = "error",
        var data: T? = null
) {


    val isSuccess: Boolean
        get() = code == ResultCode.OK

    companion object {

        fun <R> success(data: R): Result<R> {
            return Result(ResultCode.OK, "", data)
        }

        fun <R> error(code: Int, m: String): Result<R> {
            return Result<R>(code, m, null)
        }

        fun <F, T> error(s: Result<F>): Result<T> {
            return Result<T>(s.code, s.errorMessage, null)
        }
    }
}

