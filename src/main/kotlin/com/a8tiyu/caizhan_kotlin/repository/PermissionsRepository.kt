package com.a8tiyu.caizhan_kotlin.repository

import com.a8tiyu.caizhan_kotlin.entity.Permissions
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface PermissionsRepository : MongoRepository<Permissions, String> {


    @Query(value = "{'rolename': ?0},", fields = "{'permission':1,'_id':0}")
    fun findByRolename(rolename: String?): Set<Permissions>


//    fun findAllByRolenames(rolenames: HashSet<String?>): Set<Permissions> {
//
//        var permissions = HashSet<Permissions>()
//
//
//
//        return permissions
//    }

}