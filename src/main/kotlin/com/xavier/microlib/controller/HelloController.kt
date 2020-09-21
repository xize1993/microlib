package com.xavier.microlib.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Controller("/hello")
class HelloController {

    @Get(uri="/", produces=["text/plain"])
    fun index(): String {
        return "Hello Microlib"
    }

}