package telran.ashkelon2020.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.book.dto.AuthorDTO;
import telran.ashkelon2020.book.dto.BookDTO;
import telran.ashkelon2020.book.service.BookService;

@RestController
public class BookController implements BookService {
	
	@Autowired
	BookService bookService;

	@Override
	@PostMapping("/book")
	public boolean addBook(@RequestBody BookDTO bookDTO) {
		return bookService.addBook(bookDTO);
	}
	@Override
	@GetMapping("/book/{isbn}")
	public BookDTO findBookByIsbn(@PathVariable String isbn) {
		return bookService.findBookByIsbn(isbn);
	}
	@Override
	@DeleteMapping("/book/{isbn}")
	public BookDTO removeBook(@PathVariable String isbn) {
		return bookService.removeBook(isbn);
	}
	@Override
	@PutMapping("/book/{isbn}/title/{title}")
	public BookDTO updateBook(@PathVariable String isbn, @PathVariable String title) {
		return bookService.updateBook(isbn, title);
	}
	@Override
	@GetMapping("/book/author/{authorName}")
	public Iterable<BookDTO> findBooksByAuthor(@PathVariable String authorName) {
		return bookService.findBooksByAuthor(authorName);
	}
	@Override
	@GetMapping("/book/publisher/{publisherName}")
	public Iterable<BookDTO> findBooksByPublisher(@PathVariable String publisherName) {
		return bookService.findBooksByPublisher(publisherName);
	}
	@Override
	@GetMapping("/authors/{isbn}")
	public Iterable<AuthorDTO> findBookAuthors(@PathVariable String isbn) {
		return bookService.findBookAuthors(isbn);
	}
	@Override
	@GetMapping("/publishers/{authorName}")
	public Iterable<String> findPublishersByAuthor(@PathVariable String authorName) {
		return bookService.findPublishersByAuthor(authorName);
	}
	@Override
	@DeleteMapping("/author/{authorName}")
	public AuthorDTO removeAuthor(@PathVariable String authorName) {
		return bookService.removeAuthor(authorName);
	}
	
}
