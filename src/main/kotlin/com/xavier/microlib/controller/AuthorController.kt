package com.xavier.microlib.controller

import com.fasterxml.jackson.annotation.JsonView
import com.xavier.microlib.domain.Author
import com.xavier.microlib.http.request.AuthorRequest
import com.xavier.microlib.service.AuthorService
import com.xavier.microlib.utils.Views
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
    @JsonView(Views.Public::class)
    fun getAuthors(@QueryValue n: String?,
                 @QueryValue(defaultValue = "0") i: Int?,
                 @QueryValue(defaultValue = "10") s: Int?): Page<Author> {
        return authorService.findPage(n, Pageable.from(i!!, s!!))
    }

    /**
     * 著者の詳細を取得
     * @param id 著者id
     */
    @Get(uri = "/{id}")
    @JsonView(Views.Public::class)
    fun getAuthor(@PathVariable id: Int): Author {
        return authorService.findById(id)
    }

    /**
     * 著者を保存する
     * @param authorRequest 著者作成用のリクエストパラメータ
     */
    @Post(uri = "/")
    @Status(HttpStatus.CREATED)
    @JsonView(Views.Public::class)
    fun saveAuthor(@Valid @Body authorRequest: AuthorRequest): Author {
        return authorService.save(authorRequest)
    }

    /**
     * 著者を更新する
     * @param authorRequest 著者更新用のリクエストパラメータ
     */
    @Patch(uri = "/")
    @JsonView(Views.Public::class)
    fun updateAuthor(@Valid @Body authorRequest: AuthorRequest): Author {
        return authorService.update(authorRequest)
    }

    /**
     * 著者を削除する
     * @param id 著者id
     */
    @Delete(uri = "/{id}")
    @Status(HttpStatus.NO_CONTENT)
    fun deleteAuthor(@PathVariable id: Int) {
        authorService.delete(id)
    }

}