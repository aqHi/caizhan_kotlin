package com.a8tiyu.caizhan_kotlin.shiro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.realm.text.PropertiesRealm
import org.springframework.context.annotation.DependsOn
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.filter.authc.UserFilter
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
import org.apache.shiro.web.filter.authc.LogoutFilter
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter
import org.apache.shiro.web.filter.authc.AnonymousFilter
import java.util.HashMap
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.springframework.context.annotation.Bean
import javax.servlet.Filter


@Configuration
@SpringBootApplication
class ShiroConfig {
    @Bean(name = arrayOf("shiroFilter"))
    fun shiroFilter(): ShiroFilterFactoryBean {
        val shiroFilter = ShiroFilterFactoryBean()
        shiroFilter.loginUrl = "/login"
        shiroFilter.successUrl = "/index"
        shiroFilter.unauthorizedUrl = "/forbidden"
        val filterChainDefinitionMapping = HashMap<String, String>()
        filterChainDefinitionMapping.put("/", "anon")
        filterChainDefinitionMapping.put("/home", "authc,roles[guest]")
        filterChainDefinitionMapping.put("/admin", "authc,roles[admin]")
        shiroFilter.filterChainDefinitionMap = filterChainDefinitionMapping
        shiroFilter.setSecurityManager(securityManager())
        val filters = HashMap<String, Filter>()
        filters.put("anon", AnonymousFilter())
        filters.put("authc", FormAuthenticationFilter())
        filters.put("logout", LogoutFilter())
        filters.put("roles", RolesAuthorizationFilter())
        filters.put("user", UserFilter())
        shiroFilter.filters = filters
        println(shiroFilter.filters.size)
        return shiroFilter
    }

    @Bean(name = arrayOf("securityManager"))
    fun securityManager(): DefaultWebSecurityManager {
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(realm())
        return securityManager
    }

    @Bean(name = arrayOf("realm"))
    @DependsOn("lifecycleBeanPostProcessor")
    fun realm() = MyRealm()

    @Bean
    fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor {
        return LifecycleBeanPostProcessor()
    }
}


