package com.elpidoroun.bookstoremanagement.repository;

import com.elpidoroun.bookstoremanagement.model.Author;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.elpidoroun.bookstoremanagement.repository.BookRepositoryStub.generateUniqueId;

public class AuthorRepositoryStub implements AuthorRepository{

    private final List<Author> authors = new ArrayList<>();

    @Override
    public Author save(Author entity) {
        Author authorToStore;

        if (entity.getId().isEmpty()) {
            authorToStore = new Author(generateUniqueId(), entity.getName());
        } else {
            authorToStore = entity;
        }

        boolean replaced = authors.stream()
                .filter(author -> author.getId().equals(authorToStore.getId()))
                .findFirst().map(author -> {
                    int index = authors.indexOf(author);
                    authors.set(index, authorToStore);
                    return true;
                }).orElse(false);

        if (!replaced) {
            authors.add(authorToStore);
        }

        return authorToStore;
    }

    @Override
    public Optional<Author> findById(Long authorId) {
        return authors.stream()
                .filter(author -> author.getId().isPresent() && authorId.equals(author.getId().get()))
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return authors.stream()
                .anyMatch(author -> author.getId().isPresent() && id.equals(author.getId().get()));
    }

    @Override
    public List<Author> findAll() {
        return authors;
    }

    @Override
    public void deleteById(Long authorId) {
        authors.removeIf(author -> author.getId().isPresent() && authorId.equals(author.getId().get()));
    }

    @Override
    public Optional<Author> findByName(String name) {
        return authors.stream().filter(author -> author.getName().equals(name)).findFirst();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Author> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Author> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Author> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Author getOne(Long aLong) {
        return null;
    }

    @Override
    public Author getById(Long aLong) {
        return null;
    }

    @Override
    public Author getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Author> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Author> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Author> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Author> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Author> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Author> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Author, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }



    @Override
    public <S extends Author> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<Author> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Author entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Author> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Author> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return null;
    }
}
