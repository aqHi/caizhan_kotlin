package com.a8tiyu.caizhan_kotlin.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "users")
data class Users(

        @Id var id: String ?= null,
        @Field(value = "username") var username : String ?=null,
        @Field(value = "nickname") var nickname : String ?=null,
        @Field(value = "password") var password : String ?=null,
        @Field(value = "mobilephone") var mobilephone : String ?=null,
        @Field(value = "password_salt") var salt : String ?=null

)


