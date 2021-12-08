package com.roytuts.auth.exception

import javax.naming.AuthenticationException


class JwtTokenMalformedException(msg: String?) : AuthenticationException(msg) {
    companion object {
        private const val serialVersionUID = 1L
    }
}
