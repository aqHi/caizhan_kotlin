package com.a8tiyu.caizhan_kotlin.controller

import com.a8tiyu.caizhan_kotlin.entity.Users
import com.a8tiyu.caizhan_kotlin.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import redis.clients.jedis.JedisPool


@RestController
@RequestMapping("/admin")
class ConversionController {

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var jedisPool: JedisPool


    @RequestMapping("/users")
    fun admin(): MutableList<Users> {

        println()
        var jedis  = jedisPool.resource
        var a = jedis["aaa"]
        println(a)
        println()

        return usersRepository.findAll()
    }


}