package com.a8tiyu.caizhan_kotlin

import com.a8tiyu.caizhan_kotlin.controller.AuthController
import com.a8tiyu.caizhan_kotlin.entity.Coupons
import com.a8tiyu.caizhan_kotlin.entity.Roles
import com.a8tiyu.caizhan_kotlin.repository.CouponsRepository
import com.a8tiyu.caizhan_kotlin.repository.RolesRepository
import com.a8tiyu.caizhan_kotlin.repository.UsersRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class CaizhanKotlinApplicationTests {

    @Autowired
    lateinit var couponsRepository: CouponsRepository

    @Autowired
    lateinit var usersRepository: UsersRepository


    @Autowired
    lateinit var rolesRepository: RolesRepository

    @Test
    fun contextLoads() {
    }


    @Test
    fun getAllCoupons() {
        var coupons: MutableList<Coupons> = couponsRepository.findAll()
        coupons.forEach { value -> println(value.promotion?.promotion_name) }
    }

    @Test
    fun ResultTest() {
        var auth = AuthController()
//        println(auth.login().toString())
    }


    @Test
    fun getPassword() = println(usersRepository.findPasswordByUsername("admin"))

    @Test
    fun findRolesByUsername() {


        var roles = rolesRepository.findByUsername("admin")
                .mapTo(HashSet<String?>()) { it -> it.rolename }

        roles.forEach({ println(it) })


    }




}
