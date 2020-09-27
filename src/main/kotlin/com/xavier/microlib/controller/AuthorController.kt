package com.xavier.microlib.controller

import com.xavier.microlib.http.response.AuthorResponse
import com.xavier.microlib.http.request.AuthorRequest
import com.xavier.microlib.service.AuthorService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.validation.Valid

/**
 * 著者APIのコントローラー
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Validated
@Controller("/author")
class AuthorController(val authorService: AuthorService) {

    /**
     * 著者一覧を取得
     * @param n 著者名
     * @param i ページインディクス
     * @param s ページサイズ
     */
    @Get(uri = "{?n,i,s}")
    fun getAuthors(@QueryValue n: String?,
                 @QueryValue(defaultValue = "0") i: Int?,
                 @QueryValue(defaultValue = "10") s: Int?): Page<AuthorResponse> {
        return authorService.findPage(n, Pageable.from(i!!, s!!))
    }

    /**
     * 著者の詳細を取得
     * @param id 著者id
     */
    @Get(uri = "/{id}")
    fun getAuthor(@PathVariable id: Int): AuthorResponse {
        return authorService.findById(id)
    }

    /**
     * 著者を保存する
     * @param authorRequest 著者作成用のリクエストパラメータ
     */
    @Status(HttpStatus.CREATED)
    @Post(uri = "/")
    fun saveAuthor(@Valid @Body authorRequest: AuthorRequest): AuthorResponse {
        return authorService.save(authorRequest)
    }

    /**
     * 著者を更新する
     * @param authorRequest 著者更新用のリクエストパラメータ
     */
    @Patch(uri = "/")
    fun updateAuthor(@Valid @Body authorRequest: AuthorRequest): AuthorResponse {
        return authorService.update(authorRequest)
    }

    /**
     * 著者を削除する
     * @param id 著者id
     */
    @Status(HttpStatus.NO_CONTENT)
    @Delete(uri = "/{id}")
    fun deleteAuthor(@PathVariable id: Int) {
        authorService.delete(id)
    }

}