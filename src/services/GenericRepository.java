package services;

import java.util.*;
import java.util.function.Predicate;

public class GenericRepository<T> implements Repository<T> {

    private Map<String, T> storage;
    private java.util.function.Function<T, String> idExtractor;

    public GenericRepository(java.util.function.Function<T, String> idExtractor) {
        this.storage = new HashMap<>();
        this.idExtractor = idExtractor;
    }

    @Override
    public void add(T entity) {
        String id = idExtractor.apply(entity);
        storage.put(id, entity);
    }

    @Override
    public void remove(String id) {
        storage.remove(id);
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean exists(String id) {
        return storage.containsKey(id);
    }

    @Override
    public int count() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }


    public List<T> findWhere(Predicate<T> condition) {
        List<T> results = new ArrayList<>();
        for (T entity : storage.values()) {
            if (condition.test(entity)) {
                results.add(entity);
            }
        }
        return results;
    }

    public void addAll(List<? extends T> entities) {
        for (int i = 0; i < entities.size(); i++) {
            add(entities.get(i));
        }
    }

    public void exportTo(List<? super T> destination) {
        destination.addAll(storage.values());
    }

    public int countWhere(Predicate<? super T> condition) {
        int count = 0;
        for (T entity : storage.values()) {
            if (condition.test(entity)) {
                count++;
            }
        }
        return count;
    }
}