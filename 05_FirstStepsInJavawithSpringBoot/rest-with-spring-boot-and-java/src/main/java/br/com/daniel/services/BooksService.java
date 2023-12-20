package br.com.daniel.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daniel.DTO.BooksDto;
import br.com.daniel.controller.BooksController;
import br.com.daniel.exceptions.RequiredObjectIsNullException;
import br.com.daniel.exceptions.ResourceNotFoundException;
import br.com.daniel.model.Book;
import br.com.daniel.repositories.BookRepository;

@Service
public class BooksService {

	@Autowired
	BookRepository repository;
	
	public BooksDto findById(Long id) {
		 Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
		 BooksDto bookDto = new ModelMapper().map(book, BooksDto.class);

		 bookDto.add(linkTo(methodOn(BooksController.class).findById(id)).withSelfRel());
		 
		 return bookDto;
	}

	public List<BooksDto> findAll() {
		List<Book> books = repository.findAll();
		var booksDto = books.stream().map(
				book -> new ModelMapper().map(book, BooksDto.class)).collect(Collectors.toList());

		 booksDto.stream()
	 		.forEach(b -> b.add(linkTo(methodOn(BooksController.class)
	 		.findById(b.getId())).withSelfRel()));
		
		return booksDto;
	}
	
	public BooksDto newBook(BooksDto booksDto) {
		if(booksDto == null) throw new RequiredObjectIsNullException();

		booksDto.setLaunchDate(LocalDateTime.now());
		Book book = new ModelMapper().map(booksDto, Book.class);
		repository.save(book);
		booksDto.setId(book.getId());
		
		booksDto.add(linkTo(methodOn(BooksController.class).findById(booksDto.getId())).withSelfRel());

		return booksDto;
	}
	
	public BooksDto update(BooksDto booksDto) {
		if(booksDto == null) throw new RequiredObjectIsNullException();
		Book book = repository.findById(booksDto.getId()).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));

		booksDto.setLaunchDate(LocalDateTime.now());
		BeanUtils.copyProperties(booksDto, book);
		BooksDto newBook = new ModelMapper().map(repository.save(book), BooksDto.class);
		newBook.add(linkTo(methodOn(BooksController.class).findById(newBook.getId())).withSelfRel());
		return newBook;
	}
	
	public void delete(Long id) {
		repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
		repository.deleteById(id);
	}
}
