package com.xavier.microlib.repository

import com.xavier.microlib.model.Admin
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Repository
interface AdminRepository : CrudRepository<Admin, Int>  {

}