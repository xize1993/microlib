package com.xavier.microlib.utils

import com.xavier.microlib.domain.Author
import com.xavier.microlib.domain.Book
import com.xavier.microlib.http.response.AuthorResponse
import com.xavier.microlib.http.response.BookResponse

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
object DtoUtils {

    fun convertBookDto(book: Book): BookResponse {
        return BookResponse(
                book.id!!,
                book.title,
                book.isbn,
                book.authorId,
                book.subject,
                book.publicationDate,
                book.haveCover,
                book.price,
                book.description,
                book.pageCount
        )
    }

    fun convertAuthorDto(author: Author): AuthorResponse {
        return AuthorResponse(
                author.id!!,
                author.authorName,
                author.authorNameKana,
                author.birthday,
                author.description
        )
    }

}