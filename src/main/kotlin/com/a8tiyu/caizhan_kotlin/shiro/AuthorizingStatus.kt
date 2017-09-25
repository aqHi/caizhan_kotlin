package com.a8tiyu.caizhan_kotlin.shiro

class AuthorizingResult{


    val SUCCESS_CODE = 0
    val SUCCESS_MSG = "Login Success."

    val NULL_USERNAME_CODE = -3
    val NULL_USERNAME_MSG = "Null usernames are not allowed."

    val UNKNOWN_ACCOUNT_CODE = -2
    val UNKNOWN_ACCOUNT_MSG = "Unknown Account."

    val INCORRECT_CREDENTIALS_CODE = -1
    val INCORRECT_CREDENTIALS_MSG = "Incorrect Credentials."

}


enum class AuthorizingStatus{
    SUCCESS,NULL_USERNAME,UNKNOWN_ACCOUNT,TOO_MANY_USERS
}