package com.xavier.microlib.controller

import com.xavier.microlib.domain.dto.BookDto
import com.xavier.microlib.domain.option.BookSaveAndUpdateRequest
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.net.URLEncoder
import java.time.LocalDate
import javax.inject.Inject

@MicronautTest
class BookControllerTest() {

    @Inject
    @field:Client("/microlib")
    lateinit var client: RxHttpClient

    /**
     * 書籍：CRUDテスト
     */
    @Test
    @Order(1)
    fun testBookCRUD() {
        /*
            1、書籍を作成
         */
        val bookSaveRequest = BookSaveAndUpdateRequest(null, "テストユニット", "1234567890123", 1, "IT", LocalDate.now().plusDays(5),
                null, BigDecimal(1800), "テストユニットより作成", 200)
        val response = client.toBlocking().exchange(HttpRequest.POST("/book", bookSaveRequest), BookDto::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response.status)

        // レスポンスバーディー：BookDto
        assertNotNull(response.body.get().id)
        assertEquals("テストユニット", response.body.get().title)

        // 発行されたbookIdを記録
        val newBookId = response.body.get().id

        /*
            2、書籍を取得
         */
        val response2 = client.toBlocking().exchange("/book/${newBookId}", BookDto::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response2.status)

        // レスポンスバーディー：BookDto
        assertEquals(newBookId, response2.body.get().id)

        /*
            3、書籍を更新
         */
        val bookUpdateRequest = BookSaveAndUpdateRequest(newBookId, "テストユニット更新", "1234567890123", 1, "IT", LocalDate.now().plusDays(5),
                null, BigDecimal(1800), "テストユニットより更新", 200)
        val response3 = client.toBlocking().exchange(HttpRequest.PATCH("/book", bookUpdateRequest), BookDto::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response3.status)

        // レスポンスバーディー：BookDto
        assertEquals("テストユニット更新", response3.body.get().title)

        /*
            4、書籍を削除
         */
        val response4 = client.toBlocking().exchange(HttpRequest.DELETE("/book/${newBookId}", null), String::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response4.status)

        // レスポンスバーディー：削除成功のメッセージ
        assertEquals("書籍が削除されました。", response4.body.get())

//        // 再取得テスト TODO
//        val response2 = client.toBlocking().exchange("/book/${newBookId}", Book::class.java)
//
//        // レスポンスコード：404
//        assertEquals(HttpStatus.NOT_FOUND, response2.status)
    }

    /**
     * 書籍一覧検索:検察パラメータなし
     */
    @Test
    @Order(2)
    fun testGetBooks () {
        val response = client.toBlocking().exchange("/book?i=0&s=20", List::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response.status)

        // レスポンスバーディー：List
        assertTrue(response.body.get().isNotEmpty())
    }

    /**
     * 書籍一覧検索:タイトルで検索
     */
    @Test
    @Order(3)
    fun testGetBooksByTitle () {
        val p = URLEncoder.encode("テストユニット", "utf-8")
        val response = client.toBlocking().exchange("/book?t=${p}&i=0&s=10", List::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response.status)

        // レスポンスバーディー：List
        assertTrue(response.body.get().isNotEmpty())
    }

    /**
     * 書籍一覧検索:著者で検索
     */
    @Test
    @Order(4)
    fun testGetBooksByAuthorId () {
        val response = client.toBlocking().exchange("/book?a=1&i=0&s=10", List::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response.status)

        // レスポンスバーディー：List
        assertTrue(response.body.get().isNotEmpty())
    }

}