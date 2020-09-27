package com.xavier.microlib.controller

import com.xavier.microlib.http.request.BookRequest
import com.xavier.microlib.http.response.BookResponse
import com.xavier.microlib.service.BookService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.http.server.types.files.SystemFile
import io.micronaut.validation.Validated
import javax.validation.Valid

/**
 * 書籍APIのコントローラー
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Validated
@Controller("/book")
class BookController(val bookService: BookService) {

    /**
     * 書籍一覧を取得
     * @param t 書籍タイトル
     * @param a 著者id
     * @param i ページインディクス
     * @param s ページサイズ
     */
    @Get(uri = "{?t,a,i,s}")
    fun getBooks(@QueryValue t: String? = null,
                 @QueryValue a: Int?,
                 @QueryValue(defaultValue = "0") i: Int?,
                 @QueryValue(defaultValue = "10") s: Int?): Page<BookResponse> {
        return bookService.findPage(t, a, Pageable.from(i!!, s!!))
    }

    /**
     * 書籍詳細を取得
     * @param id 書籍id
     */
    @Get(uri = "/{id}")
    fun getBook(@PathVariable id: Int): BookResponse {
        return bookService.findById(id)
    }

    /**
     * 書籍を保存する
     * @param bookRequest 書籍作成用のリクエストパラメータ
     */
//    @Status(HttpStatus.CREATED)
//    @Post(uri = "/", consumes = [MediaType.MULTIPART_FORM_DATA])
//    fun saveBook(@Valid @Body bookRequest: BookRequest): BookResponse {
//        return bookService.save(bookRequest)
//    }
    @Status(HttpStatus.CREATED)
    @Post(uri = "/", consumes = [MediaType.MULTIPART_FORM_DATA])
    fun saveBook(@Body bookRequest: BookRequest, @Body imgFile: CompletedFileUpload?): BookResponse {
        return bookService.save(bookRequest, imgFile)
    }

    /**
     * 書籍を更新する
     * @param bookRequest 書籍更新用のリクエストパラメータ
     */
    @Patch(uri = "/", consumes = [MediaType.MULTIPART_FORM_DATA])
    fun updateBook(@Valid @Body bookRequest: BookRequest, @Body imgFile: CompletedFileUpload?): BookResponse {
        return bookService.update(bookRequest, imgFile)
    }

    /**
     * 書籍を削除する
     * @param id 書籍id
     */
    @Status(HttpStatus.NO_CONTENT)
    @Delete(uri = "/{id}")
    fun deleteBook(@PathVariable id: Int) {
        bookService.delete(id)
    }

    /**
     * 書籍の表紙画像を取得
     * @param id 書籍id
     */
    @Get(uri = "/cover/{id}")
    fun getBookCoverImg(@PathVariable id: Int): SystemFile {
        return bookService.getImage(id)
    }

}