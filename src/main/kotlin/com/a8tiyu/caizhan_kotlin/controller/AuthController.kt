package com.a8tiyu.caizhan_kotlin.controller

import com.a8tiyu.caizhan_kotlin.util.Result
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.*
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController {

    private val loger = LoggerFactory.getLogger(AuthController::class.java)


    @RequestMapping("/login", method = arrayOf(RequestMethod.POST))
    fun login(username: String, password: String): Result<Any> {
        var usernamePasswordToken = UsernamePasswordToken(username, password)
        return doLogin(usernamePasswordToken)

    }


    @Throws(AuthenticationException::class)
    private fun doLogin(usernamePasswordToken: UsernamePasswordToken): Result<Any> {
        var currentUser = SecurityUtils.getSubject()

        try {
            currentUser.login(usernamePasswordToken)
            return if (currentUser.isAuthenticated) {
                loger.info("登录成功")
                Result(0, "success", currentUser.principal)

            } else {
                usernamePasswordToken.clear()
                loger.info("登录失败")
                Result(-1, "error", "Username Required")

            }
        } catch (e: AccountException) {
            return Result(-1, "error", "Username Required")
        } catch (e: UnknownAccountException) {
            return Result(-1, "error", "Unknown Account")
        } catch (e: IncorrectCredentialsException) {
            return Result(-1, "error", "Incorrect Credentials")
        }


    }

}