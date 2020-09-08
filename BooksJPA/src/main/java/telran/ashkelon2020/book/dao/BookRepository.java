package telran.ashkelon2020.book.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.ashkelon2020.book.model.Author;
import telran.ashkelon2020.book.model.Book;
import telran.ashkelon2020.book.model.Publisher;

public interface BookRepository extends JpaRepository<Book, String> {

	Stream<Book> findByAuthors(Author author);

	Stream<Book> findByPublisher(Publisher publisher);

	void deleteByAuthors(Author author);

}
