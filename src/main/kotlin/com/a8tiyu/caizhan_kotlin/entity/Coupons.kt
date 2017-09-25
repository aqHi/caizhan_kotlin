package com.a8tiyu.caizhan_kotlin.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field


@Document(collection = "coupons")
data class Coupons(
        @Id var id: String? = null,
        @Field(value = "c_time") var cTime: String? = null,
        @Field(value = "m_time") var mTime: String? = null,
        @Field(value = "coupon_code") var couponCode: String? = null,
        @Field(value = "full_money") var fullMoney: Float? = 100.0f,
        @Field(value = "reduce_money") var reduceMoney: Float? = .0f,
        @Field(value = "expire_days") var expireDays: Int? = 0,
        @Field var promotion: Promotions? = null

)