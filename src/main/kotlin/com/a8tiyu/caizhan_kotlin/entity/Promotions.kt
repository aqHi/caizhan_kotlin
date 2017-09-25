package com.a8tiyu.caizhan_kotlin.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "promotions")
data class Promotions(
        @Id var id: String? = null,
        @Field var promotion_name: String? = null,
        @Field var creator_name: String? = null,
        @Field var c_time: String? = null,
        @Field var last_modifier_name: String? = null,
        @Field var m_time: String? = null,
        @Field var begin_time: String? = null,
        @Field var end_time: String? = null,
        @Field var activity_type: String? = null,
        @Field var remark: String? = null,
        @Field var status: String? = null

)