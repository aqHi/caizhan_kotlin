package com.a8tiyu.caizhan_kotlin.controller
import com.a8tiyu.caizhan_kotlin.entity.Coupons
import com.a8tiyu.caizhan_kotlin.entity.Users
import com.a8tiyu.caizhan_kotlin.repository.CouponsRepository
import com.a8tiyu.caizhan_kotlin.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UsersController() {

    @Autowired
    lateinit var usersRepository : UsersRepository

    @Autowired
    lateinit var couponsRepository: CouponsRepository

    @GetMapping("/")
    fun findAll() = "Hello"


    @RequestMapping("/coupon/{couponCode}")
    fun getCoupon(@PathVariable couponCode : String) : Coupons = couponsRepository.findByCouponCode(couponCode)

    @RequestMapping("/coupons")
    fun finAllCoupons():MutableList<Coupons> = couponsRepository.findAll()

    @GetMapping("/hello")
    fun welcome(): MutableList<Users> = usersRepository.findAll()

    @RequestMapping("/{name}")
    fun findByName(@PathVariable name : String) :Users =usersRepository.findByUsername(name)





}