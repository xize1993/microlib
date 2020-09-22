package com.xavier.microlib.controller

import com.xavier.microlib.domain.dto.AuthorDto
import com.xavier.microlib.domain.option.AuthorSaveAndUpdateRequest
import com.xavier.microlib.service.AuthorService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*

/**
 * 著者APIのコントローラー
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Controller("/author")
class AuthorController(val authorService: AuthorService) {

    /**
     * 著者一覧を取得
     */
    @Get(uri = "{?n,i,s}")
    fun getAuthors(@QueryValue n: String?,
                 @QueryValue(defaultValue = "0") i: Int?,
                 @QueryValue(defaultValue = "10") s: Int?): Page<AuthorDto> {
        return authorService.findPage(n, Pageable.from(i!!, s!!))
    }

    /**
     * 著者の詳細を取得
     */
    @Get(uri = "/{id}")
    fun getAuthor(@PathVariable id: Int): AuthorDto {
        return authorService.findById(id)
    }

    /**
     * 著者を保存する
     */
    @Post(uri = "/")
    fun saveAuthor(@Body authorSaveAndUpdateRequest: AuthorSaveAndUpdateRequest): AuthorDto {
        return authorService.save(authorSaveAndUpdateRequest)
    }

    /**
     * 著者を更新する
     */
    @Patch(uri = "/")
    fun updateAuthor(@Body authorSaveAndUpdateRequest: AuthorSaveAndUpdateRequest): AuthorDto {
        return authorService.update(authorSaveAndUpdateRequest)
    }

    /**
     * 著者を削除する
     */
    @Delete(uri = "/{id}")
    fun deleteAuthor(@PathVariable id: Int): String {
        authorService.delete(id)
        return "著者が削除されました。"
    }

}