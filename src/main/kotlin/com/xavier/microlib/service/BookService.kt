package com.xavier.microlib.service

import com.xavier.microlib.model.Book
import com.xavier.microlib.repository.BookRepository
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
     * 単特の書籍を検索する
     */
    fun findById(id: Int): Book = bookRepository.findById(id).orElseThrow()

    /**
     * 一頁の書籍データを返却する
     */
    fun findPage(): List<Book> = bookRepository.findAll().toList()

    /**
     * 書籍を保存する
     */
    fun save(book: Book) = bookRepository.save(book)

}