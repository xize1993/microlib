package com.xavier.microlib.controller

import com.xavier.microlib.domain.dto.BookDto
import com.xavier.microlib.domain.option.BookSaveAndUpdateRequest
import com.xavier.microlib.service.BookService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*

/**
 * 書籍APIのコントローラー
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Controller("/book")
class BookController(val bookService: BookService) {

    /**
     * 書籍一覧を取得
     */
    @Get(uri = "{?t,a,i,s}")
    fun getBooks(@QueryValue t: String?,
                 @QueryValue a: Int?,
                 @QueryValue(defaultValue = "0") i: Int?,
                 @QueryValue(defaultValue = "10") s: Int?): Page<BookDto> {
        return bookService.findPage(t, a, Pageable.from(i!!, s!!))
    }

    /**
     * 書籍詳細を取得
     */
    @Get(uri = "/{id}")
    fun getBook(@PathVariable id: Int): BookDto {
        return bookService.findById(id)
    }

    /**
     * 書籍を保存する
     */
    @Post(uri = "/")
    fun saveBook(@Body bookSaveAndUpdateRequest: BookSaveAndUpdateRequest): BookDto {
        return bookService.save(bookSaveAndUpdateRequest)
    }

    /**
     * 書籍を更新する
     */
    @Patch(uri = "/")
    fun updateBook(@Body bookSaveAndUpdateRequest: BookSaveAndUpdateRequest): BookDto {
        return bookService.update(bookSaveAndUpdateRequest)
    }

    /**
     * 書籍を削除する
     */
    @Delete(uri = "/{id}")
    fun deleteBook(@PathVariable id: Int): String {
        bookService.delete(id)
        return "書籍が削除されました。"
    }

}