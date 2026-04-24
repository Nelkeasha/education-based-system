package services;

import java.util.List;
import java.util.Optional;

/**
 * Generic Repository Interface.
 * Demonstrates: Generics, Type Safety, Reusability
 *
 * T - the type of entity this repository manages
 */
public interface Repository<T> {

    void add(T entity);

    void remove(String id);

    Optional<T> findById(String id);

    List<T> findAll();

    boolean exists(String id);

    int count();

    void clear();
}