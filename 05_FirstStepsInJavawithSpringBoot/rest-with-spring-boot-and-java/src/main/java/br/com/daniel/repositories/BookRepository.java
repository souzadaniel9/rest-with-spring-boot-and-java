package br.com.daniel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.daniel.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
