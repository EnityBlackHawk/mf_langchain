package org.mf.langchain.service;

import org.springframework.data.jpa.repository.JpaRepository;

public class CRUDService<TEntity, TPk, TRepo extends JpaRepository<TEntity, TPk>> {

    private final TRepo repository;

    public CRUDService(TRepo repository) {
        this.repository = repository;
    }

    public TEntity create(TEntity entity) {
        return repository.save(entity);
    }

    public TEntity read(TPk id) {
        return repository.findById(id).orElse(null);
    }

    public TEntity update(TEntity entity) {
        return repository.save(entity);
    }

    public void delete(TPk id) {
        repository.deleteById(id);
    }

    public TEntity getLast() {
        var list = repository.findAll();
        return list.get(list.size() - 1);
    }

}
