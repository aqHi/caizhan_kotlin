package com.a8tiyu.caizhan_kotlin.controller

import com.a8tiyu.caizhan_kotlin.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin")
class ConversionController {

    @Autowired
    lateinit var usersRepository: UsersRepository


    @RequestMapping("/users")
    fun admin() = usersRepository.findAll()


}