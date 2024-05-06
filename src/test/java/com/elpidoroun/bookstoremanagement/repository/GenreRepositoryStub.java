package com.elpidoroun.bookstoremanagement.repository;

import com.elpidoroun.bookstoremanagement.model.Genre;
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

public class GenreRepositoryStub implements GenreRepository{

    private final List<Genre> genres = new ArrayList<>();

    @Override
    public Genre save(Genre entity) {
        Genre genreToStore;

        if (entity.getId().isEmpty()) {
            genreToStore = new Genre(generateUniqueId(), entity.getName());
        } else {
            genreToStore = entity;
        }

        boolean replaced = genres.stream()
                .filter(genre -> genre.getId().equals(genreToStore.getId()))
                .findFirst().map(genre -> {
                    int index = genres.indexOf(genre);
                    genres.set(index, genreToStore);
                    return true;
                }).orElse(false);

        if (!replaced) {
            genres.add(genreToStore);
        }

        return genreToStore;
    }

    @Override
    public Optional<Genre> findById(Long genreId) {
        return genres.stream()
                .filter(genre -> genre.getId().isPresent() && genreId.equals(genre.getId().get()))
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return genres.stream()
                .anyMatch(genre -> genre.getId().isPresent() && id.equals(genre.getId().get()));
    }

    @Override
    public List<Genre> findAll() {
        return genres;
    }

    @Override
    public void deleteById(Long genreId) {
        genres.removeIf(genre -> genre.getId().isPresent() && genreId.equals(genre.getId().get()));
    }

    @Override
    public Optional<Genre> findByName(String name) {
        return genres.stream().filter(genre -> genre.getName().equals(name)).findFirst();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Genre> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Genre> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Genre> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Genre getOne(Long aLong) {
        return null;
    }

    @Override
    public Genre getById(Long aLong) {
        return null;
    }

    @Override
    public Genre getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Genre> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Genre> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Genre> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Genre> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Genre> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Genre> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Genre, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Genre> List<S> saveAll(Iterable<S> entities) {
        return null;
    }
    @Override
    public List<Genre> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Genre entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Genre> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Genre> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Genre> findAll(Pageable pageable) {
        return null;
    }

}
