package com.xavier.microlib.repository

import com.xavier.microlib.model.Book
import com.xavier.microlib.model.BookDto
import edu.umd.cs.findbugs.annotations.NonNull
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository
import java.util.*

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Repository
interface BookRepository : PageableRepository<Book, Int> {

//    @NonNull fun findOne(id: Int): Optional<BookDto>
//
//    fun findAllToDto(): List<BookDto>

}