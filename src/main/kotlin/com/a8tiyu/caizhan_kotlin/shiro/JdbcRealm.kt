//package com.a8tiyu.caizhan_kotlin.shiro
//
///*
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing,
// * software distributed under the License is distributed on an
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied.  See the License for the
// * specific language governing permissions and limitations
// * under the License.
// */
//
//
//import org.apache.shiro.authc.*
//import org.apache.shiro.authz.AuthorizationException
//import org.apache.shiro.authz.AuthorizationInfo
//import org.apache.shiro.authz.SimpleAuthorizationInfo
//import org.apache.shiro.config.ConfigurationException
//import org.apache.shiro.realm.AuthorizingRealm
//import org.apache.shiro.subject.PrincipalCollection
//import org.apache.shiro.util.ByteSource
//import org.apache.shiro.util.JdbcUtils
//import org.slf4j.LoggerFactory
//import java.sql.Connection
//import java.sql.PreparedStatement
//import java.sql.ResultSet
//import java.sql.SQLException
//import java.util.*
//import javax.sql.DataSource
//
//
///**
// * Realm that allows authentication and authorization via JDBC calls.  The default queries suggest a potential schema
// * for retrieving the user's password for authentication, and querying for a user's roles and permissions.  The
// * default queries can be overridden by setting the query properties of the realm.
// *
// *
// * If the default implementation
// * of authentication and authorization cannot handle your schema, this class can be subclassed and the
// * appropriate methods overridden. (usually [.doGetAuthenticationInfo],
// * [.getRoleNamesForUser], and/or [.getPermissions]
// *
// *
// * This realm supports caching by extending from [org.apache.shiro.realm.AuthorizingRealm].
// *
// * @since 0.2
// */
//class JdbcRealm : AuthorizingRealm() {
//
//    /*--------------------------------------------
//    |    I N S T A N C E   V A R I A B L E S    |
//    ============================================*/
//    protected var dataSource: DataSource
//
//    protected var authenticationQuery = DEFAULT_AUTHENTICATION_QUERY
//
//    protected var userRolesQuery = DEFAULT_USER_ROLES_QUERY
//
//    protected var permissionsQuery = DEFAULT_PERMISSIONS_QUERY
//
//    protected var permissionsLookupEnabled = false
//
//    protected var saltStyle = SaltStyle.NO_SALT
//
//    /**
//     * Password hash salt configuration.
//     *  * NO_SALT - password hashes are not salted.
//     *  * CRYPT - password hashes are stored in unix crypt format.
//     *  * COLUMN - salt is in a separate column in the database.
//     *  * EXTERNAL - salt is not stored in the database. [.getSaltForUser] will be called
//     * to get the salt
//     */
//    enum class SaltStyle {
//        NO_SALT, CRYPT, COLUMN, EXTERNAL
//    }
//
//    /*--------------------------------------------
//    |         C O N S T R U C T O R S           |
//    ============================================*/
//
//    /*--------------------------------------------
//    |  A C C E S S O R S / M O D I F I E R S    |
//    ============================================*/
//
//    /**
//     * Sets the datasource that should be used to retrieve connections used by this realm.
//     *
//     * @param dataSource the SQL data source.
//     */
//    fun setDataSource(dataSource: DataSource) {
//        this.dataSource = dataSource
//    }
//
//    /**
//     * Overrides the default query used to retrieve a user's password during authentication.  When using the default
//     * implementation, this query must take the user's username as a single parameter and return a single result
//     * with the user's password as the first column.  If you require a solution that does not match this query
//     * structure, you can override [.doGetAuthenticationInfo] or
//     * just [.getPasswordForUser]
//     *
//     * @param authenticationQuery the query to use for authentication.
//     * @see .DEFAULT_AUTHENTICATION_QUERY
//     */
//    fun setAuthenticationQuery(authenticationQuery: String) {
//        this.authenticationQuery = authenticationQuery
//    }
//
//    /**
//     * Overrides the default query used to retrieve a user's roles during authorization.  When using the default
//     * implementation, this query must take the user's username as a single parameter and return a row
//     * per role with a single column containing the role name.  If you require a solution that does not match this query
//     * structure, you can override [.doGetAuthorizationInfo] or just
//     * [.getRoleNamesForUser]
//     *
//     * @param userRolesQuery the query to use for retrieving a user's roles.
//     * @see .DEFAULT_USER_ROLES_QUERY
//     */
//    fun setUserRolesQuery(userRolesQuery: String) {
//        this.userRolesQuery = userRolesQuery
//    }
//
//    /**
//     * Overrides the default query used to retrieve a user's permissions during authorization.  When using the default
//     * implementation, this query must take a role name as the single parameter and return a row
//     * per permission with three columns containing the fully qualified name of the permission class, the permission
//     * name, and the permission actions (in that order).  If you require a solution that does not match this query
//     * structure, you can override [.doGetAuthorizationInfo] or just
//     * [.getPermissions]
//     *
//     *
//     * **Permissions are only retrieved if you set [.permissionsLookupEnabled] to true.  Otherwise,
//     * this query is ignored.**
//     *
//     * @param permissionsQuery the query to use for retrieving permissions for a role.
//     * @see .DEFAULT_PERMISSIONS_QUERY
//     *
//     * @see .setPermissionsLookupEnabled
//     */
//    fun setPermissionsQuery(permissionsQuery: String) {
//        this.permissionsQuery = permissionsQuery
//    }
//
//    /**
//     * Enables lookup of permissions during authorization.  The default is "false" - meaning that only roles
//     * are associated with a user.  Set this to true in order to lookup roles **and** permissions.
//     *
//     * @param permissionsLookupEnabled true if permissions should be looked up during authorization, or false if only
//     * roles should be looked up.
//     */
//    fun setPermissionsLookupEnabled(permissionsLookupEnabled: Boolean) {
//        this.permissionsLookupEnabled = permissionsLookupEnabled
//    }
//
//    /**
//     * Sets the salt style.  See [.saltStyle].
//     *
//     * @param saltStyle new SaltStyle to set.
//     */
//    fun setSaltStyle(saltStyle: SaltStyle) {
//        this.saltStyle = saltStyle
//        if (saltStyle == SaltStyle.COLUMN && authenticationQuery == DEFAULT_AUTHENTICATION_QUERY) {
//            authenticationQuery = DEFAULT_SALTED_AUTHENTICATION_QUERY
//        }
//    }
//
//    /*--------------------------------------------
//    |               M E T H O D S               |
//    ============================================*/
//
//    @Throws(AuthenticationException::class)
//    override fun doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo {
//
//        val upToken = token as UsernamePasswordToken
//        val username = upToken.username ?: throw AccountException("Null usernames are not allowed by this realm.")
//
//        // Null username is invalid
//
//        var conn: Connection? = null
//        var info: SimpleAuthenticationInfo? = null
//        try {
//            conn = dataSource.connection
//
//            var password: String? = null
//            var salt: String? = null
//            when (saltStyle) {
//                JdbcRealm.SaltStyle.NO_SALT -> password = getPasswordForUser(conn, username)[0]
//                JdbcRealm.SaltStyle.CRYPT ->
//                    // TODO: separate password and hash from getPasswordForUser[0]
//                    throw ConfigurationException("Not implemented yet")
//            //break;
//                JdbcRealm.SaltStyle.COLUMN -> {
//                    val queryResults = getPasswordForUser(conn, username)
//                    password = queryResults[0]
//                    salt = queryResults[1]
//                }
//                JdbcRealm.SaltStyle.EXTERNAL -> {
//                    password = getPasswordForUser(conn, username)[0]
//                    salt = getSaltForUser(username)
//                }
//            }
//
//            if (password == null) {
//                throw UnknownAccountException("No account found for user [$username]")
//            }
//
//            info = SimpleAuthenticationInfo(username, password.toCharArray(), name)
//
//            if (salt != null) {
//                info.credentialsSalt = ByteSource.Util.bytes(salt)
//            }
//
//        } catch (e: SQLException) {
//            val message = "There was a SQL error while authenticating user [$username]"
//            if (log.isErrorEnabled) {
//                log.error(message, e)
//            }
//
//            // Rethrow any SQL errors as an authentication exception
//            throw AuthenticationException(message, e)
//        } finally {
//            JdbcUtils.closeConnection(conn)
//        }
//
//        return info
//    }
//
//    @Throws(SQLException::class)
//    private fun getPasswordForUser(conn: Connection?, username: String): Array<String> {
//
//        val result: Array<String>
//        var returningSeparatedSalt = false
//        when (saltStyle) {
//            JdbcRealm.SaltStyle.NO_SALT, JdbcRealm.SaltStyle.CRYPT, JdbcRealm.SaltStyle.EXTERNAL -> result = arrayOfNulls(1)
//            else -> {
//                result = arrayOfNulls(2)
//                returningSeparatedSalt = true
//            }
//        }
//
//        var ps: PreparedStatement? = null
//        var rs: ResultSet? = null
//        try {
//            ps = conn!!.prepareStatement(authenticationQuery)
//            ps!!.setString(1, username)
//
//            // Execute query
//            rs = ps.executeQuery()
//
//            // Loop over results - although we are only expecting one result, since usernames should be unique
//            var foundResult = false
//            while (rs!!.next()) {
//
//                // Check to ensure only one row is processed
//                if (foundResult) {
//                    throw AuthenticationException("More than one user row found for user [$username]. Usernames must be unique.")
//                }
//
//                result[0] = rs.getString(1)
//                if (returningSeparatedSalt) {
//                    result[1] = rs.getString(2)
//                }
//
//                foundResult = true
//            }
//        } finally {
//            JdbcUtils.closeResultSet(rs)
//            JdbcUtils.closeStatement(ps)
//        }
//
//        return result
//    }
//
//    /**
//     * This implementation of the interface expects the principals collection to return a String username keyed off of
//     * this realm's [name][.getName]
//     *
//     * @see .getAuthorizationInfo
//     */
//    override fun doGetAuthorizationInfo(principals: PrincipalCollection?): AuthorizationInfo {
//
//        //null usernames are invalid
//        if (principals == null) {
//            throw AuthorizationException("PrincipalCollection method argument cannot be null.")
//        }
//
//        val username = getAvailablePrincipal(principals) as String
//
//        var conn: Connection? = null
//        var roleNames: Set<String>? = null
//        var permissions: Set<String>? = null
//        try {
//            conn = dataSource.connection
//
//            // Retrieve roles and permissions from database
//            roleNames = getRoleNamesForUser(conn, username)
//            if (permissionsLookupEnabled) {
//                permissions = getPermissions(conn, username, roleNames)
//            }
//
//        } catch (e: SQLException) {
//            val message = "There was a SQL error while authorizing user [$username]"
//            if (log.isErrorEnabled) {
//                log.error(message, e)
//            }
//
//            // Rethrow any SQL errors as an authorization exception
//            throw AuthorizationException(message, e)
//        } finally {
//            JdbcUtils.closeConnection(conn)
//        }
//
//        val info = SimpleAuthorizationInfo(roleNames)
//        info.stringPermissions = permissions
//        return info
//
//    }
//
//    @Throws(SQLException::class)
//    protected fun getRoleNamesForUser(conn: Connection?, username: String): Set<String> {
//        var ps: PreparedStatement? = null
//        var rs: ResultSet? = null
//        val roleNames = LinkedHashSet<String>()
//        try {
//            ps = conn!!.prepareStatement(userRolesQuery)
//            ps!!.setString(1, username)
//
//            // Execute query
//            rs = ps.executeQuery()
//
//            // Loop over results and add each returned role to a set
//            while (rs!!.next()) {
//
//                val roleName = rs.getString(1)
//
//                // Add the role to the list of names if it isn't null
//                if (roleName != null) {
//                    roleNames.add(roleName)
//                } else {
//                    if (log.isWarnEnabled) {
//                        log.warn("Null role name found while retrieving role names for user [$username]")
//                    }
//                }
//            }
//        } finally {
//            JdbcUtils.closeResultSet(rs)
//            JdbcUtils.closeStatement(ps)
//        }
//        return roleNames
//    }
//
//    @Throws(SQLException::class)
//    protected fun getPermissions(conn: Connection?, username: String, roleNames: Collection<String>?): Set<String> {
//        var ps: PreparedStatement? = null
//        val permissions = LinkedHashSet<String>()
//        try {
//            ps = conn!!.prepareStatement(permissionsQuery)
//            for (roleName in roleNames!!) {
//
//                ps!!.setString(1, roleName)
//
//                var rs: ResultSet? = null
//
//                try {
//                    // Execute query
//                    rs = ps.executeQuery()
//
//                    // Loop over results and add each returned role to a set
//                    while (rs!!.next()) {
//
//                        val permissionString = rs.getString(1)
//
//                        // Add the permission to the set of permissions
//                        permissions.add(permissionString)
//                    }
//                } finally {
//                    JdbcUtils.closeResultSet(rs)
//                }
//
//            }
//        } finally {
//            JdbcUtils.closeStatement(ps)
//        }
//
//        return permissions
//    }
//
//    protected fun getSaltForUser(username: String): String {
//        return username
//    }
//
//    companion object {
//
//        //TODO - complete JavaDoc
//
//        /*--------------------------------------------
//    |             C O N S T A N T S             |
//    ============================================*/
//        /**
//         * The default query used to retrieve account data for the user.
//         */
//        protected val DEFAULT_AUTHENTICATION_QUERY = "select password from users where username = ?"
//
//        /**
//         * The default query used to retrieve account data for the user when [.saltStyle] is COLUMN.
//         */
//        protected val DEFAULT_SALTED_AUTHENTICATION_QUERY = "select password, password_salt from users where username = ?"
//
//        /**
//         * The default query used to retrieve the roles that apply to a user.
//         */
//        protected val DEFAULT_USER_ROLES_QUERY = "select role_name from user_roles where username = ?"
//
//        /**
//         * The default query used to retrieve permissions that apply to a particular role.
//         */
//        protected val DEFAULT_PERMISSIONS_QUERY = "select permission from roles_permissions where role_name = ?"
//
//        private val log = LoggerFactory.getLogger(JdbcRealm::class.java)
//    }
//
//}
