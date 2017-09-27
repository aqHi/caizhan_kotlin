package com.a8tiyu.caizhan_kotlin.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*


@Document(collection = "permissions")
data class Permissions(

        @Id var id: String? = null,
        @Field(value = "permission") var permission: String? = null,
        @Field(value = "rolename") var rolename: String? = null,
        @Field(value = "gmt_create") var gmt_create: Date? = null,
        @Field(value = "gmt_modify") var gmt_modify: Date? = null,
        @Field(value = "description") var description: String? = null

)