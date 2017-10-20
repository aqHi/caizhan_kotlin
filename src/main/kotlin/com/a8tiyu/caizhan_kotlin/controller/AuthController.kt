package com.a8tiyu.caizhan_kotlin.controller

import com.a8tiyu.caizhan_kotlin.entity.Users
import com.a8tiyu.caizhan_kotlin.template.Result
import com.a8tiyu.caizhan_kotlin.template.ResultCode
import com.auth0.jwt.JWT
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.*
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*
import org.springframework.context.support.ClassPathXmlApplicationContext




@RestController
class AuthController {

    private val loger = LoggerFactory.getLogger(AuthController::class.java)




    /**
     * 登陆请求
     */
    @RequestMapping("/login", method = arrayOf(RequestMethod.POST))
    fun login(username: String, password: String): Result<Any> {

        val ac = ClassPathXmlApplicationContext("spring.xml")

        var currentUser = SecurityUtils.getSubject()

        return if (!currentUser.isAuthenticated) {
            var usernamePasswordToken = UsernamePasswordToken(username, password)
            doLogin(usernamePasswordToken)
        } else {
            loger.info("重复登录")
            Result.error(ResultCode.DUPLICATE_LOGIN, "Duplicate Login.")

        }

    }


    @RequestMapping("/gologin")
    fun gologin() = Result.error<String>(code = ResultCode.UNAUTHORIZED, m = "You need login first.")

    /**
     * 未授权请求
     */
    @RequestMapping("/forbidden")
    fun unauthorized() = Result.error<String>(code = ResultCode.UNAUTHORIZED, m = "Unauthorized Request.")


    /**
     * 登陆逻辑
     */
    @Throws(AuthenticationException::class)
    private fun doLogin(usernamePasswordToken: UsernamePasswordToken): Result<Any> {
        var currentUser = SecurityUtils.getSubject()

        try {
            currentUser.login(usernamePasswordToken)
            return if (currentUser.isAuthenticated) {
                loger.info("登录成功!${currentUser.hasRole("admin")}")

                var payload = HashMap<String ,Any>()
                var date = Date()
                payload.put("uid", currentUser.principal)
                payload.put("iat",date.time)
                payload.put("ext",date.time+1000*60*60*2)
                JWT.create().withIssuer("")

                Result.success(currentUser.principal)

            } else {
                usernamePasswordToken.clear()
                loger.info("登录失败")
                Result(-1, "error", Users(username = currentUser.principal.toString()))

            }
        } catch (e: AccountException) {
            return Result(-1, "error", "Username Required.")
        } catch (e: UnknownAccountException) {
            return Result.error(ResultCode.UNKNOWN_ACCOUNT, "Unknown Account.")
        } catch (e: IncorrectCredentialsException) {
            return Result.error(ResultCode.INCORRECT_CREDENTIALS, "Incorrect Credentials.")
        }


    }

}