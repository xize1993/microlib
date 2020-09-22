package com.xavier.microlib.domain

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.data.annotation.Where
import org.hibernate.annotations.DynamicUpdate
import java.math.BigDecimal
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
@DynamicUpdate
data class Book (

        /** ID */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        /** タイトル */
        var title: String?,

        /** 国際標準図書番号 */
        var isbn: String?,

        /** 著者ID */
        var authorId: Int,

        /** 種別 */
        var subject: String?,

        /** 出版日 */
        var publicationDate: LocalDate?,

        /** 表紙格納パス */
        var coverImgUrl: String?,

        /** 価格 */
        var price: BigDecimal?,

        /** 内容紹介 */
        var description: String?,

        /** ページ数 */
        var pageCount: Int?,

        /** 論理フラグ */
        var flag: Boolean,

        /** 作成ユーザID */
        var createUserId: Int,

        /** 作成日時 */
        @DateCreated
        var createTime: LocalDateTime?,

        /** 更新ユーザID */
        var updateUserId: Int,

        /** 更新日時 */
        @DateUpdated
        var updateTime: LocalDateTime?
)