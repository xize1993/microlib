package com.xavier.microlib.service

import com.xavier.microlib.domain.Book
import com.xavier.microlib.domain.dto.BookDto
import com.xavier.microlib.domain.option.BookSaveAndUpdateRequest
import com.xavier.microlib.repository.BookRepository
import com.xavier.microlib.utils.DtoUtils
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 書籍のサービスクラス
 * @author: Xavier
 * @create: 2020-09-20
 **/
@Singleton
class BookService {

    @Inject
    lateinit var bookRepository: BookRepository

    /**
     * 一頁の書籍データを返却する
     */
    fun findPage(title: String?, authorId: Int?, pageable: Pageable): Page<BookDto> {
        return bookRepository.findPage(title, authorId, pageable)
    }

    /**
     * 単特の書籍を検索する
     */
    fun findById(id: Int): BookDto = bookRepository.findOne(id)

    /**
     * 書籍を保存する
     */
    fun save(bookSaveAndUpdateRequest: BookSaveAndUpdateRequest): BookDto {
        val book = Book(null, bookSaveAndUpdateRequest.title, bookSaveAndUpdateRequest.isbn, bookSaveAndUpdateRequest.authorId,
                bookSaveAndUpdateRequest.subject, bookSaveAndUpdateRequest.publicationDate, bookSaveAndUpdateRequest.coverImgUrl,
                bookSaveAndUpdateRequest.price, bookSaveAndUpdateRequest.description, bookSaveAndUpdateRequest.pageCount,
                true, 1, null, 1, null)
        bookRepository.save(book)
        return DtoUtils.convertBookDto(book)
    }

    /**
     * 書籍を更新する
     */
    fun update(bookSaveAndUpdateRequest: BookSaveAndUpdateRequest): BookDto {
        val book = bookRepository.findById(bookSaveAndUpdateRequest.id!!).get()
        book.title = bookSaveAndUpdateRequest.title
        book.isbn = bookSaveAndUpdateRequest.isbn
        book.authorId = bookSaveAndUpdateRequest.authorId
        book.subject = bookSaveAndUpdateRequest.subject
        book.publicationDate = bookSaveAndUpdateRequest.publicationDate
        book.coverImgUrl = bookSaveAndUpdateRequest.coverImgUrl
        book.price = bookSaveAndUpdateRequest.price
        book.pageCount = bookSaveAndUpdateRequest.pageCount
        bookRepository.update(book)
        return DtoUtils.convertBookDto(book)
    }

    /**
     * 書籍を削除する
     */
    fun delete(id: Int) {
        val book = bookRepository.findById(id).get()
        book.flag = false
        bookRepository.update(book)
    }

}
