package com.a8tiyu.caizhan_kotlin.shiro

import com.a8tiyu.caizhan_kotlin.entity.Permissions
import com.a8tiyu.caizhan_kotlin.entity.Users
import com.a8tiyu.caizhan_kotlin.repository.PermissionsRepository
import com.a8tiyu.caizhan_kotlin.repository.RolesRepository
import com.a8tiyu.caizhan_kotlin.repository.UsersRepository
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationException
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired


class MyRealm : AuthorizingRealm() {

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var rolesRepository: RolesRepository

    @Autowired
    lateinit var permissionsRepository: PermissionsRepository


    private var log = LoggerFactory.getLogger(MyRealm::class.java)

    protected var permissionsLookupEnabled = false


    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(token: AuthenticationToken?): AuthenticationInfo {


        var upToken = token as UsernamePasswordToken

        val username = upToken.username ?: throw AccountException("Null usernames are not allowed by this realm.")

        if (upToken.password == null) throw AccountException("Invid password")


        var user: Users? = usersRepository.findPasswordByUsername(username) ?: throw UnknownAccountException("No account found for user [$username]")


        var info = SimpleAuthenticationInfo(username, user?.password, name)
        log.info("username:$username,password:$(user.password) ,salt:")


        return info

    }

    override fun doGetAuthorizationInfo(principals: PrincipalCollection?): AuthorizationInfo {
        if (principals == null) {
            throw AuthorizationException("PrincipalCollection method argument cannot be null.")
        }

        val username = getAvailablePrincipal(principals) as String

        val roleNames = rolesRepository.findByUsername(username)
                .mapTo(HashSet<String?>()) { it -> it.rolename }

        var permissions = HashSet<String?>()

        var perm = HashSet<Permissions>()

        if (permissionsLookupEnabled) {
            roleNames.forEach { it -> perm.plus(permissionsRepository.findByRolename(it)) }
            permissions = perm.mapTo(permissions) { it -> it.permission }
        }


        var info = SimpleAuthorizationInfo(roleNames)
        info.stringPermissions = permissions

        log.info("--username:{} :", username)
        log.info("----roles:{} :", roleNames)
        log.info("----permissions:{} :", permissions)

        return info
    }

}