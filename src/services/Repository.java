package services;

import java.util.List;
import java.util.Optional;


public interface Repository<T> {

    void add(T entity);

    void remove(String id);

    Optional<T> findById(String id);

    List<T> findAll();

    boolean exists(String id);

    int count();

    void clear();
}