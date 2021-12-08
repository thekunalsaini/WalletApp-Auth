package com.roytuts.auth.model



import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Email
import javax.validation.constraints.Min

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Document("auth")
@Validated
class ManagerAuth {

    @Id
    private  var id: Int =0

    @Email(message = "email is not valid")
    @NotNull(message = "email is mandatory")
    private var email: String? = null
    @Size(min = 6, max = 200, message
    = "password should not be less than 6")
    @NotNull(message = "password is mandatory")
    private var password: String? = null

    constructor(id: Int, email: String?, password: String?) {
        this.id = id
        this.email = email
        this.password = password

    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }



    override fun toString(): String {
        return "ManagerAuth(id=$id, email=$email, password=$password)"
    }


}