package com.xavier.microlib.service

import com.xavier.microlib.domain.Book
import com.xavier.microlib.exception.NotFoundException
import com.xavier.microlib.http.request.BookRequest
import com.xavier.microlib.http.response.BookResponse
import com.xavier.microlib.repository.BookRepository
import com.xavier.microlib.utils.DtoUtils
import io.micronaut.context.annotation.Property
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.http.server.types.files.SystemFile
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

    @field:Property(name = "microlib.book.cover.directory")
    var imgDirectory: String? = null

    /**
     * 一頁の書籍データを返却する
     */
    fun findPage(title: String?, authorId: Int?, pageable: Pageable): Page<BookResponse> {
        return bookRepository.findPage(title, authorId, pageable)
    }

    /**
     * 単特の書籍を検索する
     */
    fun findById(id: Int): BookResponse = bookRepository.findOne(id)?.let {
        return it
    } ?: throw NotFoundException("書籍が検索できません。")

    /**
     * 書籍を保存する
     */
    fun save(bookRequest: BookRequest, imgFile: CompletedFileUpload?): BookResponse {
        // 画像ファイルを保存
        val coverImgUrl = imgFile?.let {
            saveImage(it)
        }

        val book = Book(null, bookRequest.title ?: "", bookRequest.isbn, bookRequest.authorId,
                bookRequest.subject, bookRequest.publicationDate, coverImgUrl?.let { true } ?: false, coverImgUrl,
                bookRequest.price, bookRequest.description, bookRequest.pageCount,
                true, 1, null, 1, null)
        bookRepository.save(book)
        return DtoUtils.convertBookDto(book)
    }

    /**
     * 書籍を更新する
     */
    fun update(bookRequest: BookRequest, imgFile: CompletedFileUpload?): BookResponse {
        // 画像ファイルを保存
        val coverImgUrl = imgFile?.let {
            saveImage(it)
        }

        val book = bookRepository.findById(bookRequest.id!!).get()
        book.title = bookRequest.title
        coverImgUrl?.let {
            book.coverImgUrl = it
            book.haveCover = true
        }
        book.isbn = bookRequest.isbn
        book.authorId = bookRequest.authorId
        book.subject = bookRequest.subject
        book.publicationDate = bookRequest.publicationDate
        book.price = bookRequest.price
        book.pageCount = bookRequest.pageCount
        book.description = bookRequest.description
        bookRepository.update(book)
        return DtoUtils.convertBookDto(book)
    }

    /**
     * 書籍を削除する
     */
    fun delete(id: Int) {
        val book = bookRepository.findById(id).orElseThrow { NotFoundException("書籍が検索できません。") }
        book.flag = false
        bookRepository.update(book)
    }

    /**
     * 書籍の表紙画像を保存する
     */
    fun saveImage(imgUploadFile: CompletedFileUpload): String {
        // 画像ディレクトリを確保
        val directory = File(imgDirectory!!)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        val imgFile = File("$imgDirectory/${time}.jpg")
        imgFile.createNewFile()
        imgFile.writeBytes(imgUploadFile.bytes)
        return imgFile.absolutePath
    }

    /**
     * 書籍の表紙画像を取得
     */
    fun getImage(id: Int): SystemFile {
        val book = bookRepository.findById(id).orElseThrow { NotFoundException("書籍表紙が取得できません。") }
        book.coverImgUrl?.let {
            val file = File(book.coverImgUrl!!)
            if (file.exists()) {
                return SystemFile(file).attach(file.name)
            } else {
                throw NotFoundException("書籍表紙が取得できません。")
            }
        } ?: throw NotFoundException("書籍表紙が取得できません。")
    }

}
