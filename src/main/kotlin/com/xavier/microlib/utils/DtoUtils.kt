package com.xavier.microlib.utils

import com.xavier.microlib.domain.Author
import com.xavier.microlib.domain.Book
import com.xavier.microlib.domain.dto.AuthorDto
import com.xavier.microlib.domain.dto.BookDto

/**
 *
 * @author: Xavier
 * @create: 2020-09-20
 **/
object DtoUtils {

    fun convertBookDto(book: Book): BookDto {
        return BookDto(
                book.id!!,
                book.title,
                book.isbn,
                book.authorId,
                book.subject,
                book.publicationDate,
                book.coverImgUrl,
                book.price,
                book.description,
                book.pageCount
        )
    }

    fun convertAuthorDto(author: Author): AuthorDto {
        return AuthorDto(
                author.id!!,
                author.authorName,
                author.authorNameKana,
                author.birthday,
                author.description
        )
    }

}