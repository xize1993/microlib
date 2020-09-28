package com.xavier.microlib.controller

import com.xavier.microlib.domain.Book
import io.micronaut.core.io.ResourceLoader
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.multipart.MultipartBody
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.net.URLEncoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@MicronautTest
class BookControllerTest() {

    @Inject
    @field:Client("/microlib")
    lateinit var client: RxHttpClient

    @Inject
    lateinit var resourceLoader: ResourceLoader

    /**
     * 書籍：CRUDテスト
     */
    @Test
    @Order(1)
    fun testBookCRUD() {
        /*
            1、書籍を作成 TODO file upload
         */
//        val file = File(resourceLoader.getResource("classpath:image/book-cover.jpg").get().file)
//        val createRequestBody = MultipartBody
//                .builder()
//                .addPart("id", "")
//                .addPart("title", "テストユニット")
////                .addPart("imgFile", "book-cover.jpg", MediaType.IMAGE_JPEG_TYPE, file)
//                .addPart("authorId", "1")
//                .addPart("isbn", "1234567890123")
//                .addPart("subject", "テスト")
//                .addPart("publicationDate", "2020-12-12")
//                .addPart("price", "1800")
//                .addPart("pageCount", "200")
//                .addPart("description", "テストユニットより作成")
//                .build()
        val createRequestBody = buildMultipartBodyForCreate()
        val response = client.toBlocking().exchange(HttpRequest.POST("/book", createRequestBody)
                .contentType(MediaType.MULTIPART_FORM_DATA_TYPE), Book::class.java)

        // レスポンスコード：201
        assertEquals(HttpStatus.CREATED, response.status)

        // レスポンスバーディー：BookDto
        assertNotNull(response.body.get().id)
        assertEquals("テストユニット", response.body.get().title)

        // 発行されたbookIdを記録
        val newBookId = response.body.get().id

        /*
            2、書籍を取得
         */
        val response2 = client.toBlocking()
                .exchange("/book/${newBookId}", Book::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response2.status)

        // レスポンスバーディー：BookDto
        assertEquals(newBookId, response2.body.get().id)

        /*
            3、書籍を更新
         */
        val updateRequestBody = buildMultipartBodyForUpdate(newBookId.toString(), "テストユニット更新", "テストユニットより更新")
        val response3 = client.toBlocking().exchange(HttpRequest.PATCH("/book", updateRequestBody)
                .contentType(MediaType.MULTIPART_FORM_DATA_TYPE), Book::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response3.status)

        // レスポンスバーディー：BookDto
        assertEquals("テストユニット更新", response3.body.get().title)

        /*
            4、書籍を削除
         */
        val response4 = client.toBlocking()
                .exchange(HttpRequest.DELETE("/book/${newBookId}", null), String::class.java)

        // レスポンスコード：204
        assertEquals(HttpStatus.NO_CONTENT, response4.status)

        /*
            5、再取得テスト
         */
        val httpClientResponseException = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange("/book/${newBookId}", Book::class.java)
        }
        // レスポンスコード：404
        assertEquals(HttpStatus.NOT_FOUND, httpClientResponseException.status)
    }

    /**
     * 書籍一覧検索:検察パラメータなし
     */
    @Test
    @Order(2)
    fun testGetBooks() {
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
    fun testGetBooksByTitle() {
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
    fun testGetBooksByAuthorId() {
        val response = client.toBlocking().exchange("/book?a=1&i=0&s=10", List::class.java)

        // レスポンスコード：200
        assertEquals(HttpStatus.OK, response.status)

        // レスポンスバーディー：List
        assertTrue(response.body.get().isNotEmpty())
    }

    /**
     * 入力チェック
     */
    @ParameterizedTest
    @MethodSource("validationDataProvider")
    @Order(5)
    fun testValidated(createRequestBody: MultipartBody) {
        val httpClientResponseException = assertThrows<HttpClientResponseException> {
            client.toBlocking().exchange(HttpRequest.POST("/book", createRequestBody)
                    .contentType(MediaType.MULTIPART_FORM_DATA_TYPE), Book::class.java)
        }
        // レスポンスコード：400
        assertEquals(HttpStatus.BAD_REQUEST, httpClientResponseException.status)
    }

    companion object {
        @JvmStatic
        fun validationDataProvider() = listOf(
                // タイトル長さ違反
                buildMultipartBodyForValidTitle("テ".repeat(101)),
                // ISBN長さ違反
                buildMultipartBodyForValidIsbn("1".repeat(9)),
                buildMultipartBodyForValidIsbn("1".repeat(14)),
                // 著者idが負数
                buildMultipartBodyForValidAuthorId("-1"),
                // 種別長さ違反
                buildMultipartBodyForValidSubject("テ".repeat(101)),
                // 過去の日付
                buildMultipartBodyForValidPublicationDate("2010-12-12"),
                // 価格が負数
                buildMultipartBodyForValidPrice("-1"),
                // 書籍ページ数が負数
                buildMultipartBodyForValidPageCount("-1"),
                // 内容紹介長さ違反
                buildMultipartBodyForValidDescription("テ".repeat(2001))
        )

        fun buildMultipartBodyForCreate(): MultipartBody {
            return buildMultipartBodyDefault(null, null, null, null, null, null, null, null, null)
        }

        fun buildMultipartBodyForUpdate(id: String, title: String, description: String): MultipartBody {
            return buildMultipartBodyDefault(id, title, null, null, null, null, null, null, description)
        }

        private fun buildMultipartBodyForValidTitle(title: String): MultipartBody {
            return buildMultipartBodyDefault(null, title, null, null, null, null, null, null, null)
        }

        private fun buildMultipartBodyForValidIsbn(isbn: String): MultipartBody {
            return buildMultipartBodyDefault(null, null, null, isbn, null, null, null, null, null)
        }

        private fun buildMultipartBodyForValidAuthorId(authorId: String): MultipartBody {
            return buildMultipartBodyDefault(null, null, authorId, null, null, null, null, null, null)
        }

        private fun buildMultipartBodyForValidSubject(subject: String): MultipartBody {
            return buildMultipartBodyDefault(null, null, null, null, subject, null, null, null, null)
        }

        private fun buildMultipartBodyForValidPublicationDate(publicationDate: String): MultipartBody {
            return buildMultipartBodyDefault(null, null, null, null, null, publicationDate, null, null, null)
        }

        private fun buildMultipartBodyForValidPrice(price: String): MultipartBody {
            return buildMultipartBodyDefault(null, null, null, null, null, null, price, null, null)
        }

        private fun buildMultipartBodyForValidPageCount(pageCount: String): MultipartBody {
            return buildMultipartBodyDefault(null, null, null, null, null, null, null, pageCount, null)
        }

        private fun buildMultipartBodyForValidDescription(description: String): MultipartBody {
            return buildMultipartBodyDefault(null, null, null, null, null, null, null, null, description)
        }

        private fun buildMultipartBodyDefault(id: String?,
                                              title: String?,
                                              authorId: String?,
                                              isbn: String?,
                                              subject: String?,
                                              publicationDate: String?,
                                              price: String?,
                                              pageCount: String?,
                                              description: String?): MultipartBody {
            return MultipartBody
                    .builder()
                    .addPart("id", id.orEmpty())
                    .addPart("title", title ?: "テストユニット")
                    .addPart("authorId", authorId ?: "1")
                    .addPart("isbn", isbn ?: "1234567890123")
                    .addPart("subject", subject ?: "テスト")
                    .addPart("publicationDate", publicationDate
                            ?: LocalDate.now().plusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE))
                    .addPart("price", price ?: "1800")
                    .addPart("pageCount", pageCount ?: "200")
                    .addPart("description", description ?: "テストユニットより作成")
                    .build()
        }
    }

}