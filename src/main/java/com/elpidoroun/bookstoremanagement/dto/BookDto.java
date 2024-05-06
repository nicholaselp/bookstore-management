package com.elpidoroun.bookstoremanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class BookDto implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    @NotNull(message = "title is missing")
    private String title;

    @JsonProperty("author")
    @NotNull(message = "author is missing")
    private String author;

    @JsonProperty("genre")
    @NotNull(message = "genre is missing")
    private String genre;
    @JsonProperty("price")
    @NotNull(message = "price is missing")
    private Double price;

    public BookDto() { }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor(){ return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDto bookDto)) return false;
        return Objects.equals(id, bookDto.id) && Objects.equals(title, bookDto.title) && Objects.equals(author, bookDto.author) && Objects.equals(genre, bookDto.genre) && Objects.equals(price, bookDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre, price);
    }
}
