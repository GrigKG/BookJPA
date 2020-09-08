package telran.ashkelon2020.book.service;

import telran.ashkelon2020.book.dto.AuthorDTO;
import telran.ashkelon2020.book.dto.BookDTO;

public interface BookService {

	boolean addBook(BookDTO bookDTO);

	BookDTO findBookByIsbn(String isbn);

	BookDTO removeBook(String isbn);

	BookDTO updateBook(String isbn, String title);

	Iterable<BookDTO> findBooksByAuthor(String authorName);

	Iterable<BookDTO> findBooksByPublisher(String publisherName);

	Iterable<AuthorDTO> findBookAuthors(String isbn);

	Iterable<String> findPublishersByAuthor(String authorName);

	AuthorDTO removeAuthor(String authorName);

}
