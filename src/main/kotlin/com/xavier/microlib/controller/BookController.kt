package com.xavier.microlib.controller

import com.xavier.microlib.model.Book
import com.xavier.microlib.model.BookDto
import com.xavier.microlib.service.BookService
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

/**
 * 書籍APIのコントローラー
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Controller("/book")
class BookController(val bookService: BookService) {

    @Get(uri = "/")
    fun getBooks(): List<Book> {
        return bookService.findPage()
    }

    @Get(uri = "/{id}")
    fun getBook(@PathVariable id: Int): Book {
        return bookService.findById(id)
    }

    @Post(uri = "/")
    fun saveBook(@Body book: Book): Int {
        bookService.save(book)
        return book.id!!
    }

}