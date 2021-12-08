package com.roytuts.auth.dao

import com.roytuts.auth.model.ManagerAuth
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface IManagerDao: MongoRepository<ManagerAuth, Int> {
    //fun findByUsername(userName:String): ManagerAuth?
    fun existsByEmail(userName:String):Boolean
    /*@Query(value = "{'_id' : ?0}", fields = "{'password' : 0}")
    fun getUsernameById(id:Int):String*/
    @Query("{email: ?0}")
    fun findByEmail(email: String?): ManagerAuth?
}