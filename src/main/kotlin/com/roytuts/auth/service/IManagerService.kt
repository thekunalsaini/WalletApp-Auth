package com.roytuts.auth.service

import com.roytuts.auth.model.ManagerAuth

interface IManagerService {

    fun registerManager(managerAuth: ManagerAuth): ManagerAuth
    fun loginManager(managerAuth: ManagerAuth): ManagerAuth?
    fun getemail(s: String):ManagerAuth?
}