package com.roytuts.auth.service

import com.roytuts.auth.dao.IManagerDao
import com.roytuts.auth.exception.Warning
import com.roytuts.auth.model.ManagerAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class ManageServiceImpl: IManagerService {

    @Autowired
    private lateinit var iManagerDao: IManagerDao

    private val passwordEncoder = BCryptPasswordEncoder()

    override fun registerManager(managerAuth: ManagerAuth): ManagerAuth {

        if(!iManagerDao.existsById(managerAuth.getId())){
            if(!managerAuth.getEmail()?.let { iManagerDao.existsByEmail(it) }!!){
                val newManagerAuth = ManagerAuth(managerAuth.getId(), managerAuth.getEmail(), passwordEncoder.encode(managerAuth.getPassword()))
                return iManagerDao.save(newManagerAuth)
            }else{
                throw Warning("This Username is Already Exists")
            }
        }else{
            throw Warning("This Id is already In-Use.")
        }
    }

    override fun loginManager(managerAuth: ManagerAuth): ManagerAuth? {
        //var l: String? = managerAuth.getEmail();
        println(managerAuth)
        println("====================================================")
        if(managerAuth.getEmail()?.let { iManagerDao.existsByEmail(it) } == true) {
            return managerAuth.getEmail()?.let { iManagerDao.findByEmail(it) }
        }else{
            throw Warning("Profile Not Exists or Check Username Again")
        }
    }




    override fun getemail(s:String): ManagerAuth?{
        return iManagerDao.findByEmail(s)
    }

}