package com.xavier.microlib.repository

import com.xavier.microlib.model.Author
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Repository
interface AuthorRepository : PageableRepository<Author, Int> {

}