package br.com.daniel.unittests.mapper.mocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.daniel.DTO.BooksDto;
import br.com.daniel.model.Book;

public class MockBook {

	public Book mockEntity() {
		return mockBook(0);
	}

	public BooksDto mockDto() {
		return mockDto(0);
	}

	public List<Book> mockEntityList() {
		List<Book> persons = new ArrayList<Book>();
		for (int i = 0; i < 14; i++) {
			persons.add(mockBook(i));
		}
		return persons;
	}

	public List<BooksDto> mockDtoList() {
		List<BooksDto> books = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			books.add(mockDto(i));
		}
		return books;
	}

	public Book mockBook(Integer number) {
		Book book = new Book();
		book.setAuthor("Author Test" + number);
		book.setLaunchDate(LocalDateTime.now());
		book.setPrice(number + 0.0);
		book.setId(number.longValue());
		book.setTitle("Title Test" + number);
		return book;
	}

	public BooksDto mockDto(Integer number) {
		BooksDto book = new BooksDto();
		book.setAuthor("Author Test" + number);
		book.setLaunchDate(LocalDateTime.now());
		book.setPrice(number + 0.0);
		book.setId(number.longValue());
		book.setTitle("Title Test" + number);
		return book;
	}

}