package com.xavier.microlib.http.request

import io.micronaut.core.annotation.Introspected
import io.micronaut.core.convert.format.Format
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Part
import io.micronaut.http.multipart.CompletedFileUpload
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.*

/**
 * 書籍作成用のリクエストパラメータ
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class BookRequest(
        /** Id */
        @field:PositiveOrZero(message = "この書籍がありません")
        var id: Int?,

        /** タイトル */
        @field:Size(max=100, message = "タイトルは最大{max}桁")
        var title: String?,

        /** 表紙ファイル */
//        var imgFile: CompletedFileUpload?,

        /** 国際標準図書番号 */
        @field:Size(min=10, max = 13, message = "ISBN号は{min}~{max}桁")
        var isbn: String?,

        /** 著者ID */
        @field:PositiveOrZero(message = "この著者がありません")
        var authorId: Int,

        /** 種別 */
        @field:Size(max=100, message = "種別は最大{max}桁")
        var subject: String?,

        /** 出版日 */
        @field:FutureOrPresent(message = "出版日を過去の日付に設定できません。")
        @Format("yyyy-MM-dd")
        var publicationDate: LocalDate?,

        /** 価格 */
        @field:PositiveOrZero(message = "価格を0以上設定ください")
        var price: BigDecimal?,

        /** 書籍ページ数 */
        @field:PositiveOrZero(message = "書籍ページ数を0以上設定ください")
        var pageCount: Int?,

        /** 内容紹介 */
        @field:Size(max=2000, message = "内容紹介は最大{max}桁")
        var description: String?

)