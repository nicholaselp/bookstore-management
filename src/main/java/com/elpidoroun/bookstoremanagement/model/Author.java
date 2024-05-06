package com.elpidoroun.bookstoremanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Book> bookSet = new HashSet<>();

    public Author() { }

    public Author(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this.name = requireNonNull(name, "name is missing from Author");
    }

    public Optional<Long> getId() {return Optional.ofNullable(id); }
    public String getName(){ return name; }

    public Set<Book> getBook(){ return bookSet; }

}