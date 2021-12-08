package com.roytuts.auth.restcontroller



import com.roytuts.auth.model.ManagerAuth
import com.roytuts.auth.service.ManageServiceImpl
import com.roytuts.auth.util.JwtUtil

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.constraints.NotNull


@RestController
@RequestMapping("/auth")
class AuthRestController {
    @Autowired
    private val jwtUtil: JwtUtil? = null
    @Autowired
    private lateinit var managerServiceImpl: ManageServiceImpl

    private val passwordEncoder = BCryptPasswordEncoder()

    @GetMapping("/hello")
    fun hello():String{
        return "hello"
    }
    //private lateinit var appSecret: String
//    @PostMapping("/login")
//    fun login(@RequestBody userName: String?): ResponseEntity<String> {
//        val token = jwtUtil!!.generateToken(userName)
//        return ResponseEntity(token, HttpStatus.OK)
//    }

//    @PostMapping("/register")
//    fun register(@RequestBody userName: String?): ResponseEntity<String> {
//        // Persist user to some persistent storage
//        println("Info saved...")
//        return ResponseEntity("Registered", HttpStatus.OK)
//    }


    //These register, login, logout functions are for manager

    @PostMapping("/register")
    fun registerManager(@RequestBody @Valid managerAuth: ManagerAuth): ResponseEntity<ManagerAuth>{
        return ResponseEntity(managerServiceImpl.registerManager(managerAuth),HttpStatus.OK)
    }

    @PostMapping("/login")
    fun managerAuthentication(@RequestBody @Valid managerAuth: ManagerAuth, response : HttpServletResponse): ResponseEntity<Any>{
        val manLogin = this.managerServiceImpl.loginManager(managerAuth)
        if(manLogin != null){
            if(passwordEncoder.matches(managerAuth.getPassword(),manLogin.getPassword())){

                val issuer = manLogin.getEmail()
                val token = jwtUtil!!.generateToken(issuer)
                return ResponseEntity(token, HttpStatus.OK)
            }else{
                return ResponseEntity("wrong password", HttpStatus.BAD_REQUEST)
            }
        }else{
            return ResponseEntity("Manager is NULL",HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/getemail/{id}")
    fun getemail(@PathVariable @NotNull id: String):ResponseEntity<Any>{
        return ResponseEntity(managerServiceImpl.getemail(id),
            HttpStatus.OK)
    }

}