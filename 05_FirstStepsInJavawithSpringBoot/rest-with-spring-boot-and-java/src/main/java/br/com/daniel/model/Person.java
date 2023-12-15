package br.com.daniel.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//As anotações dizem para o BD que essa é uma tabela e o nome que ela vai obter no BD
@Entity
@Table(name = "person")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // Atribui o identificador da tabela
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração de ID de maneira automática
	private Long id;

	@Column(name = "first_name", nullable = false) // Atribui um nome para a coluna e o segundo comando diz que essa tabela não pode ser null
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column
	private String address;

	@Column(length = 6) // Dizem o número máximo de caracteres que pode ter no BD
	private String gender;

	public Person() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(id, other.id);
	}

}
