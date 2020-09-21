package com.xavier.microlib.model

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.data.annotation.Where
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Entity(name = "t_book")
@Where("flag = true")
data class Book (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        var title: String?,

        var isbn: String?,

        var authorId: Int,

        var publicationDate: LocalDate?,

        var description: String?,

        var pageCount: Int?,

        var flag: Boolean,

        var createUserId: Int,

        @DateCreated
        var createTime: LocalDateTime?,

        var updateUserId: Int,

        @DateUpdated
        var updateTime: LocalDateTime?
)