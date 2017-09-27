package com.a8tiyu.caizhan_kotlin.repository

import com.a8tiyu.caizhan_kotlin.entity.Roles
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface RolesRepository: MongoRepository<Roles , String>{


    /*
    根据用户名查询拥有的角色名称
     */
    @Query(value = "{'username': ?0}," , fields = "{'rolename':1,'_id':0}")
    fun findByUsername(username : String?) : Set<Roles>
}