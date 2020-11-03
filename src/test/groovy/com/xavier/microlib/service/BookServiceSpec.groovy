package com.xavier.microlib.service

import com.xavier.microlib.domain.Book
import com.xavier.microlib.repository.AuthorRepository
import com.xavier.microlib.repository.BookRepository
import io.micronaut.context.annotation.Replaces
import io.micronaut.data.model.DefaultPage
import io.micronaut.data.model.Pageable
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class BookServiceSpec extends Specification {

    @Inject
    BookService bookService

    @Inject
    BookRepository bookRepository

    @Inject
    AuthorRepository authorRepository

    @MockBean
    @Replaces(BookRepository)
    BookRepository mockBookRepo() {
        Mock(BookRepository)
    }

    @MockBean
    @Replaces(AuthorRepository)
    AuthorRepository mockAuthorRepo(){
        Mock(AuthorRepository)
    }

    def "ブックのデータがない"() {
        when:
        def bookPage = bookService.findPage(null, null, Pageable.from(0, 10))

        then:
        1 * bookRepository.findPage(*_) >> new DefaultPage<Book>(Collections.emptyList(), Pageable.unpaged(), 0)
        bookPage.isEmpty()
    }

    def "三つのブックが返却される"() {
        when:
        def bookPage = bookService.findPage(null, null, Pageable.from(0, 10))

        then:
        interaction {
            def books = [new Book(id: 1), new Book(id: 2), new Book(id: 3)]
            1 * bookRepository.findPage(*_) >> new DefaultPage<Book>(books, Pageable.unpaged(), books.size())
        }

        and: "以下は期待のレスポンス"

        bookPage.size() == 3
    }


}
