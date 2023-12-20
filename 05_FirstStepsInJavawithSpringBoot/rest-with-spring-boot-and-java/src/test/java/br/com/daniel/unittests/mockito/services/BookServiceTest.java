package br.com.daniel.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.daniel.DTO.BooksDto;
import br.com.daniel.exceptions.RequiredObjectIsNullException;
import br.com.daniel.model.Book;
import br.com.daniel.repositories.BookRepository;
import br.com.daniel.services.BooksService;
import br.com.daniel.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	MockBook input;

	@InjectMocks
	private BooksService service;

	@Mock
	BookRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Book entity = input.mockBook(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</books/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
	//	assertEquals(, result.getLaunchDate());
		assertEquals(1.0, result.getPrice());
		assertEquals("Title Test1", result.getTitle());
	}

	@Test
	void testeCriar() {
		Book entity = input.mockBook(1);

		Book persisted = entity;
		persisted.setId(1L);

		BooksDto bookDto = input.mockDto(1);
		bookDto.setId(1L);

		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</book/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(LocalDateTime.now(), result.getLaunchDate());
		assertEquals(1.0, result.getPrice());
		assertEquals("Title Test1", result.getTitle());
	}
	
	@Test
	void testeCriarSemParametros() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, 
				() -> { service.newBook(null); });
		
		String expectedMessage = "Não é permitido persistir um objeto nulo!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		
	}

	@Test
	void testeAtualizar() {
		Book entity = input.mockBook(1);
		entity.setId(1L);
		
		Book persisted = entity;
		persisted.setId(1L);

		BooksDto bookDto = input.mockDto(1);
		bookDto.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</book/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(LocalDateTime.now(), result.getLaunchDate());
		assertEquals(1.0, result.getPrice());
		assertEquals("Title Test1", result.getTitle());
	}
	
	@Test
	void testeAtualizarSemParametros() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, 
				() -> { service.update(null); });
		
		String expectedMessage = "Não é permitido persistir um objeto nulo!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testDeletar() {
		Book entity = input.mockBook(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}
	
	@Test
	void testeFindAll() {
		List<Book> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var people = service.findAll();
		assertNotNull(people);
		assertEquals(14, people.size());
		
		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</book/1>;rel=\"self\"]"));
		assertEquals("Author Test1", result.getAuthor());
		assertEquals(LocalDateTime.now(), result.getLaunchDate());
		assertEquals(1.0, result.getPrice());
		assertEquals("Title Test1", result.getTitle());
	}
	
}
