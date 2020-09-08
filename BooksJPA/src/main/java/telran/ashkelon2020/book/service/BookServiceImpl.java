package telran.ashkelon2020.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.ashkelon2020.book.dao.AuthorRepository;
import telran.ashkelon2020.book.dao.BookRepository;
import telran.ashkelon2020.book.dao.PublisherRepository;
import telran.ashkelon2020.book.dto.AuthorDTO;
import telran.ashkelon2020.book.dto.BookDTO;
import telran.ashkelon2020.book.dto.EntityNotFoundException;
import telran.ashkelon2020.book.model.Author;
import telran.ashkelon2020.book.model.Book;
import telran.ashkelon2020.book.model.Publisher;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	AuthorRepository authorRepository;
	
	@Autowired
	PublisherRepository  publisherRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	@Transactional
	public boolean addBook(BookDTO bookDTO) {
		if(bookRepository.existsById(bookDTO.getIsbn())) return false;
		
		String publisherName = bookDTO.getPublisher();
		Publisher publisher = publisherRepository.findById(publisherName)
				.orElse(publisherRepository.save(new Publisher(publisherName)));
		
		Set<Author> authors = bookDTO.getAuthors().stream()
			    .map(a -> authorRepository.findById(a.getName()).orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
			    .collect(Collectors.toSet());
		
		Book book = new Book(bookDTO.getIsbn(), bookDTO.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	@Transactional
	public BookDTO findBookByIsbn(String isbn) {		
		Book book = bookRepository.findById(isbn).orElseThrow(()-> new EntityNotFoundException());
		return modelMapper.map(book, BookDTO.class);
	}

	@Override
	@Transactional
	public BookDTO removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> new EntityNotFoundException());
		bookRepository.delete(book);
		return modelMapper.map(book, BookDTO.class);
	}

	@Override
	@Transactional
	public BookDTO updateBook(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> new EntityNotFoundException());
		book.setTitle(title);
		return modelMapper.map(book, BookDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<BookDTO> findBooksByAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElseThrow(()-> new EntityNotFoundException());
		return bookRepository.findByAuthors(author)
				.map(b->modelMapper.map(b, BookDTO.class)).collect(Collectors.toList());

	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<BookDTO> findBooksByPublisher(String publisherName) {
		Publisher publisher = publisherRepository.findById(publisherName).orElseThrow(()-> new EntityNotFoundException());
		return bookRepository.findByPublisher(publisher)
				.map(b->modelMapper.map(b, BookDTO.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<AuthorDTO> findBookAuthors(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(()-> new EntityNotFoundException());
		return book.getAuthors().stream()
				.map(a->modelMapper.map(a, AuthorDTO.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<String> findPublishersByAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElseThrow(()-> new EntityNotFoundException());
		return bookRepository.findByAuthors(author).map(a->a.getPublisher().getPublisherName()).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public AuthorDTO removeAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElseThrow(()-> new EntityNotFoundException());
		bookRepository.deleteByAuthors(author);
		authorRepository.delete(author);
		return modelMapper.map(author, AuthorDTO.class);
	}

}