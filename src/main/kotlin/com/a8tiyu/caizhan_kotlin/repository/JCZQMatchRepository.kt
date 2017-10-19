package com.a8tiyu.caizhan_kotlin.repository

import com.a8tiyu.caizhan_kotlin.entity.JCZQMatch
import org.springframework.data.mongodb.repository.MongoRepository

interface JCZQMatchRepository : MongoRepository<JCZQMatch , String>{

}