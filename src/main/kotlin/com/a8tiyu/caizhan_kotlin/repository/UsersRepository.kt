package com.a8tiyu.caizhan_kotlin.repository

import com.a8tiyu.caizhan_kotlin.entity.Users
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query


interface UsersRepository : MongoRepository<Users, String> {

    override fun findOne(id: String?): Users

    override fun findAll(): MutableList<Users>

    /**
     * 根据用户名查询用户信息
     *
     * @return 用户实体
     */
    fun findByUsername(name:String?): Users

    /**
     * 根据用户名查询密码和盐
     * 登陆验证使用
     * @Return 用户实体
     */
    @Query(value = "{'username': ?0},",fields = "{'password':1,'password_salt':1,'_id':0}")
    fun findPasswordByUsername(name: String?):Users


    override fun <S : Users?> save(entity: S): S


}