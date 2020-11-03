package com.xavier.microlib.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import com.xavier.microlib.utils.Views
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
@JsonView
data class Book (

        /** ID */
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonView(Views.Public::class)
        var id: Int?,

        /** タイトル */
        @JsonView(Views.Public::class)
        var title: String?,

        /** 国際標準図書番号 */
        @JsonView(Views.Public::class)
        var isbn: String?,

        /** 著者 */
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "author_id", nullable = true)
        @JsonIgnoreProperties("books")
        @JsonView(Views.Public::class)
        var author: Author?,

        /** 種別 */
        @JsonView(Views.Public::class)
        var subject: String?,

        /** 出版日 */
        @JsonFormat(pattern = "yyyy-MM-dd")
        @JsonView(Views.Public::class)
        var publicationDate: LocalDate?,

        /** 表紙有無フラグ */
        @JsonView(Views.Public::class)
        var haveCover: Boolean,

        /** 表紙格納パス */
        var coverImgUrl: String?,

        /** 価格 */
        @JsonView(Views.Public::class)
        var price: BigDecimal?,

        /** 内容紹介 */
        @JsonView(Views.Public::class)
        @Column(length=2500)
        var description: String?,

        /** ページ数 */
        @JsonView(Views.Public::class)
        var pageCount: Int?,

        /** 論理フラグ */
        var flag: Boolean?,

        /** 作成ユーザID */
        var createUserId: Int?,

        /** 作成日時 */
        @DateCreated
        var createTime: LocalDateTime?,

        /** 更新ユーザID */
        var updateUserId: Int?,

        /** 更新日時 */
        @DateUpdated
        var updateTime: LocalDateTime?
)