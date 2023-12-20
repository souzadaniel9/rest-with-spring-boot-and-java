package br.com.daniel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daniel.DTO.BooksDto;
import br.com.daniel.services.BooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/books")
public class BooksController {

	@Autowired
	BooksService service;

	@Operation(summary = "Find a specific book by your ID")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BooksDto> findById(@PathVariable Long id) {
		BooksDto book = service.findById(id);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@Operation(summary = "Find all books")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<BooksDto>> findAll() {
		List<BooksDto> list = service.findAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Operation(summary = "Create a new book")
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
				 consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BooksDto> newBook(@RequestBody BooksDto booksDto) {
		BooksDto bookDto = service.newBook(booksDto);
		return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update a book")
	@PutMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
				consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BooksDto> update(@RequestBody BooksDto booksDto) {
		BooksDto bookUpdate = service.update(booksDto);
		return new ResponseEntity<>(bookUpdate, HttpStatus.OK);
	}

	@Operation(summary = "Delete specific book by your ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
