package com.a8tiyu.caizhan_kotlin.shiro

import com.a8tiyu.caizhan_kotlin.entity.Users
import com.a8tiyu.caizhan_kotlin.repository.UsersRepository
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.realm.jdbc.JdbcRealm
import org.apache.shiro.subject.PrincipalCollection
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired



class MyRealm : AuthorizingRealm() {

    @Autowired
    lateinit var usersRepository: UsersRepository


    private var log = LoggerFactory.getLogger(MyRealm::class.java)


    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(token: AuthenticationToken?): AuthenticationInfo {


        var upToken = token as UsernamePasswordToken

        val username = upToken.username ?: throw AccountException("Null usernames are not allowed by this realm.")

        if (upToken.password == null)  throw AccountException("Invid password")


        var user: Users? = usersRepository.findPasswordByUsername(username) ?: throw UnknownAccountException("No account found for user [$username]")


        var info = SimpleAuthenticationInfo(username, user?.password, name)
        log.info("username:$username,password:$(user.password) ,salt:")


        return info

    }

    override fun doGetAuthorizationInfo(principals: PrincipalCollection?): AuthorizationInfo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}