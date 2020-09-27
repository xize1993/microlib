package com.xavier.microlib.controller

import com.xavier.microlib.http.request.AuthorRequest
import com.xavier.microlib.http.response.AuthorResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.multipart.MultipartBody
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.net.URLEncoder
import java.time.LocalDate
import javax.inject.Inject

@MicronautTest
class AuthorControllerTest {

    @Inject
    @field:Client("/microlib")
    lateinit var client: RxHttpClient

    /**
     * 著者：CRUDテスト
     */
    @Test
    @Order(1)
    fun testAuthorCRUD() {
        /*
            1、著者を作成
         */
        val authorSaveRequest = AuthorRequest(null, "テスト著者", "テスト チョシャ",
                LocalDate.of(1980, 10, 1), "テストユニットより作成")
        val response = client.toBlocking().exchange(HttpRequest.POST("/author", authorSaveRequest), AuthorResponse::class.java)

        // レスポンスコード：201
        assertEquals(HttpStatus.CREATED, response.status)

        // レスポンスバーディー：AuthorDto
        Assertions.assertNotNull(response.body.get().id)
        assertEquals("テスト著者", response.body.get().authorName)

        // 発行されたauthorIdを記録
        val newAuthorId = response.body.get().id

        /*
            2、著者を取得
         */
        val response2 = client.toBlocking().exchange("/author/${newAuthorId}", AuthorResponse::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response2.status)

        // レスポンスバーディー：AuthorDto
        assertEquals(newAuthorId, response2.body.get().id)

        /*
            3、著者を更新
         */
        val authorUpdateRequest = AuthorRequest(newAuthorId, "テスト著者更新", "テスト チョシャ",
                LocalDate.of(1980, 10, 1), "テストユニットより更新")
        val response3 = client.toBlocking().exchange(HttpRequest.PATCH("/author", authorUpdateRequest), AuthorResponse::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response3.status)

        // レスポンスバーディー：AuthorDto
        assertEquals("テスト著者更新", response3.body.get().authorName)

        /*
            4、著者を削除
         */
        val response4 = client.toBlocking().exchange(HttpRequest.DELETE("/author/${newAuthorId}", null), String::class.java)

        // レスポンスコード：204
        assertEquals(HttpStatus.NO_CONTENT, response4.status)

        /*
            5、再取得テスト
         */
        val response5 = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange("/author/${newAuthorId}", AuthorResponse::class.java)
        }
        // レスポンスコード：404
        assertEquals(HttpStatus.NOT_FOUND, response5.status)
    }

    /**
     * 著者一覧検索:検察パラメータなし
     */
    @Test
    @Order(2)
    fun testGetAuthors() {
        val response = client.toBlocking().exchange("/author?i=0&s=20", List::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response.status)

        // レスポンスバーディー：List
        Assertions.assertTrue(response.body.get().isNotEmpty())
    }

    /**
     * 著者一覧検索:著者名で検索
     */
    @Test
    @Order(3)
    fun testGetAuthorsByName() {
        val p = URLEncoder.encode("テスト著者", "utf-8")
        val response = client.toBlocking().exchange("/author?n=${p}i=0&s=20", List::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response.status)

        // レスポンスバーディー：List
        Assertions.assertTrue(response.body.get().isNotEmpty())
    }


    /**
     * 入力チェック
     */
    @ParameterizedTest
    @MethodSource("validationDataProvider")
    @Order(4)
    fun testValidated(authorRequest: AuthorRequest) {
        val httpClientResponseException = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(HttpRequest.POST("/author", authorRequest), AuthorResponse::class.java)
        }
        // レスポンスコード：400
        assertEquals(HttpStatus.BAD_REQUEST, httpClientResponseException.status)
    }


    companion object {
        @JvmStatic
        fun validationDataProvider() = listOf(
                // 著者名長さ違反
                AuthorRequest(null, "テ".repeat(51), "テスト チョシャ", LocalDate.of(1980, 10, 1), "テストユニットより作成"),
                // 著者呼ぶ名長さ違反
                AuthorRequest(null, "テスト著者", "テ".repeat(51), LocalDate.of(1980, 10, 1), "テストユニットより作成"),
                // 紹介長さ違反
                AuthorRequest(null, "テスト著者", "テスト チョシャ", LocalDate.of(1980, 10, 1), "テ".repeat(1001))
        )
    }
}