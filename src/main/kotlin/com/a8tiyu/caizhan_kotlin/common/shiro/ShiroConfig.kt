package com.a8tiyu.caizhan_kotlin.common.shiro

import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.slf4j.LoggerFactory
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import java.util.*


@Configuration

class ShiroConfig {

    private val logger = LoggerFactory.getLogger(ShiroConfig::class.java)




    @Bean(name = arrayOf("shiroFilter"))
    fun shiroFilter(securityManager: SecurityManager): ShiroFilterFactoryBean {

        logger.info("注入Shiro的Web过滤器-->shiroFilter", ShiroFilterFactoryBean::class.java)

        val shiroFilter = ShiroFilterFactoryBean()

        shiroFilter.loginUrl = "/gologin"
        shiroFilter.successUrl = "/index"
        shiroFilter.unauthorizedUrl = "/forbidden"

        val filterChainDefinitionMapping = HashMap<String, String>()
        filterChainDefinitionMapping.put("/", "anon")
        filterChainDefinitionMapping.put("/home", "authc,roles[guest]")
//        filterChainDefinitionMapping.put("/admin/**", "authc,roles[admin]")
        shiroFilter.filterChainDefinitionMap = filterChainDefinitionMapping

        shiroFilter.securityManager = securityManager

        println(shiroFilter.filters.size)
        return shiroFilter
    }

    @Bean(name = arrayOf("securityManager"))
    fun securityManager(realm: MyRealm): DefaultWebSecurityManager {
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(realm)
        return securityManager
    }

    @Bean(name = arrayOf("realm"))
    @DependsOn("lifecycleBeanPostProcessor")
    fun realm() = MyRealm()

    @Bean(name = arrayOf("lifecycleBeanPostProcessor"))
    fun lifecycleBeanPostProcessor() = LifecycleBeanPostProcessor()


    @Bean(name = arrayOf("advisorAutoProxyCreator"))
    @DependsOn("lifecycleBeanPostProcessor")
    fun advisorAutoProxyCreator(): DefaultAdvisorAutoProxyCreator {
        var advisorAutoProxyCreator = DefaultAdvisorAutoProxyCreator()
        advisorAutoProxyCreator.isProxyTargetClass = true
        return advisorAutoProxyCreator

    }


}


