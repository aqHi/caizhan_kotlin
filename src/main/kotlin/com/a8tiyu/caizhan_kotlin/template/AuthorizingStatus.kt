package com.a8tiyu.caizhan_kotlin.template

class AuthorizingStatus{

    lateinit var username : String
    lateinit var cookie : String


    constructor(username : String , cookie : String){
        this.username = username
        this.cookie = cookie
        ResultCode.AUTH_ERROR

    }


}






