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
@Entity(name = "t_author")
@Where("flag = true")
data class Author (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        var authorName: String,

        var authorNameKana: String?,

        var birthday: LocalDateTime?,

        var description: String?,

        var flag: Boolean,

        var createUserId: Int,

        @DateCreated
        var createTime: LocalDateTime,

        var updateUserId: Int,

        @DateUpdated
        var updateTime: LocalDateTime
)