package com.xavier.microlib.controller

import com.xavier.microlib.service.AuthorService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

/**
 * 著者APIのコントローラー
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Controller("/author")
class AuthorController() {

    @Get(uri="/")
    fun findAuthor(): String {
        return "Example Response"
    }

}