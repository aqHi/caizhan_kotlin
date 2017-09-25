package com.a8tiyu.caizhan_kotlin.repository

import com.a8tiyu.caizhan_kotlin.entity.Coupons
import org.springframework.data.mongodb.repository.MongoRepository


interface CouponsRepository : MongoRepository<Coupons, String> {

    override fun findAll(): MutableList<Coupons>


    fun findByCouponCode(couponCode : String) : Coupons

}
