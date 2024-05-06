package com.elpidoroun.bookstoremanagement.repository;

import com.elpidoroun.bookstoremanagement.model.Book;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import static java.util.Objects.nonNull;

public class BookRepositoryStub implements BookRepository{

    private final List<Book> books = new ArrayList<>();

    AuthorRepository authorRepository = new AuthorRepositoryStub();
    GenreRepository genreRepository = new GenreRepositoryStub();

    @Override
    public Book save(Book entity) {
        Book bookToStore;

        if(entity.getId().isEmpty()){
            bookToStore = Book.builder(generateUniqueId())
                    .withTitle(entity.getTitle())
                    .withPrice(entity.getPrice())
                    .withAuthor(entity.getAuthor())
                    .withGenre(entity.getGenre())
                    .build();
        } else {
            bookToStore = entity;
        }

        boolean replaced = books.stream().filter(book -> book.getId().equals(bookToStore.getId()))
                .findFirst().map(book -> {
                    int index = books.indexOf(book);
                    books.set(index, bookToStore);
                    return true;
                }).orElse(false);

        var storedAuthor = authorRepository.save(bookToStore.getAuthor());
        var storedGenre = genreRepository.save(bookToStore.getGenre());

        var toReturn = bookToStore
                .clone()
                .withGenre(storedGenre)
                .withAuthor(storedAuthor)
                .build();

        if(!replaced){
            books.add(toReturn);
        }

        return toReturn;

    }

    @Override
    public boolean existsByAuthorId(Long authorId) {
        return books.stream()
                .anyMatch(book ->
                        nonNull(book.getAuthor())
                                && book.getAuthor().getId().isPresent()
                                && book.getAuthor().getId().get().equals(authorId));
    }

    @Override
    public boolean existsByGenreId(Long genreId) {
        return books.stream()
                .anyMatch(book ->
                        nonNull(book.getGenre())
                                && book.getGenre().getId().isPresent()
                                && book.getGenre().getId().get().equals(genreId));    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return books.stream()
                .filter(book -> book.getId().isPresent() && bookId.equals((book.getId().get())))
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return books.stream()
                .anyMatch(book -> book.getId().isPresent() && id.equals(book.getId().get()));
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public void deleteById(Long bookId) {
        books.removeIf(book -> book.getId().isPresent() && bookId.equals(book.getId().get()));
    }


    @Override
    public Page<Book> searchByTitleAuthorGenrePricewithPageable(String title, String author, String genre, Double price, Pageable pageable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Book> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Book> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Book> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Book getOne(Long aLong) {
        return null;
    }

    @Override
    public Book getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Book> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Book> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public Book getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Book> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Book> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Book> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Book> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Book, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }



    @Override
    public <S extends Book> List<S> saveAll(Iterable<S> entities) {
        return null;
    }



    @Override
    public List<Book> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }



    @Override
    public void delete(Book entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Book> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Book> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return null;
    }

    public static long generateUniqueId() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }
}
