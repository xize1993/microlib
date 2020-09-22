package com.xavier.microlib.domain.option

import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import java.time.LocalDate

/**
 * 書籍作成用のリクエストパラメータ
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Introspected
data class BookSaveAndUpdateRequest(
        /** Id */
        var id: Int?,

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
        var pageCount: Int?
)