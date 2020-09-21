package com.xavier.microlib.controller

import com.xavier.microlib.model.Book
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate


@MicronautTest
class BookControllerTest() {

    val contextPath = "/microlib"
    lateinit var client: RxHttpClient

    var embeddedServer = ApplicationContext.run(EmbeddedServer::class.java)

    @BeforeEach
    fun init() {
        client = embeddedServer.applicationContext.createBean(RxHttpClient::class.java, embeddedServer.url)
    }

    @AfterEach
    fun tearDown() {
        client.close()
    }

    @Test
    fun testGetBook() {
        val response = client.toBlocking().exchange("${contextPath}/book/1", Book::class.java)
        assertEquals(HttpStatus.OK, response.status)
        assertEquals(1, response.body.get().id)
    }

    @Test
    fun testGetBooks() {
        val response = client.toBlocking().exchange("${contextPath}/book", String::class.java)
        assertEquals(HttpStatus.OK, response.status)
    }

    @Test
    fun testSaveBook() {
        var book = Book(null, "テストユニット", "1234567890123", 1, LocalDate.now().plusDays(5),
                "テストユニットより作成", 100, true, 1, null, 1, null)
        val response = client.toBlocking().exchange(HttpRequest.POST("${contextPath}/book", book), Int::class.java)
        assertEquals(HttpStatus.OK, response.status)
        print(book)
    }

}