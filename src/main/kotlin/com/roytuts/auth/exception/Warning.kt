package com.roytuts.auth.exception

data class Warning (
    val msg: String?
): RuntimeException()