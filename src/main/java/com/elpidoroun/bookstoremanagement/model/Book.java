package com.elpidoroun.bookstoremanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.Optional;

import static com.elpidoroun.bookstoremanagement.utility.StringUtils.requireNonBlank;
import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double price;

    private Book(){}

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genre_id")
    @JsonBackReference
    private Genre genre;

    private Book(Long id, String title, Double price, Author author, Genre genre){
        this.id = id;
        this.title = requireNonBlank(title, "Title is missing");
        this.price = requireNonNull(price, "Price is missing");
        this.author = requireNonNull(author, "author is missing");
        this.genre = requireNonNull(genre, "genre is missing");
    }

    public Optional<Long> getId(){ return Optional.ofNullable(id); }
    public String getTitle(){ return title; }
    public Double getPrice(){ return price; }

    public Author getAuthor(){ return author; }
    public Genre getGenre(){ return genre; }

    public static Builder builder(){ return new Builder(); }
    public static Builder builder(Long id){
        return new Builder(id);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(price, book.price) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, author, genre);
    }

    public Builder clone(){
        return new Builder()
                .withId(this.getId().orElse(null))
                .withTitle(this.getTitle())
                .withPrice(this.getPrice())
                .withGenre(this.getGenre())
                .withAuthor(this.getAuthor());
    }

    public static class Builder {
        private Long id;
        private String title;
        private Double price;
        private Author author;
        private Genre genre;

        private Builder(){}
        private Builder(Long id){this.id = id;}

        public Builder withId(Long id){
            this.id = id;
            return this;
        }
        public Builder withTitle(String title){
            this.title = title;
            return this;
        }

        public Builder withPrice(Double price){
            this.price = price;
            return this;
        }

        public Builder withGenre(Genre genre){
            this.genre = genre;
            return this;
        }

        public Builder withAuthor(Author author){
            this.author = author;
            return this;
        }

        public Book build(){
            return new Book(id, title, price, author, genre);
        }

    }

}