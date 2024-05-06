package com.elpidoroun.bookstoremanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Book> books = new HashSet<>();

    public Genre() { }

    public Genre(@Nullable Long id, String name){
        this.id = id;
        this.name = requireNonNull(name, "Name is missing from Genre");
    }

    public Genre(String name) {
        this.name = requireNonNull(name, "name is missing from Genre");
    }

    public Optional<Long> getId() {return Optional.ofNullable(id); }
    public String getName(){ return name; }
    public Set<Book> getBooks(){ return books; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre genre)) return false;
        return Objects.equals(id, genre.id) && Objects.equals(name, genre.name) && Objects.equals(books, genre.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, books);
    }
}