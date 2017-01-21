/*
 * Copyright (C) 2016 AptiTekk, LLC. (https://AptiTekk.com/) - All Rights Reserved
 * Unauthorized copying of any part of AptiBook, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package com.aptitekk.aptictrl.core.domain.repositories;

import com.aptitekk.aptictrl.core.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@Transactional
public abstract class EntityRepositoryAbstract<T> {

    Class<T> entityType;

    @Autowired
    LogService logService;

    @PostConstruct
    private void init() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        //noinspection unchecked
        this.entityType = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @PersistenceContext
    EntityManager entityManager;

    public T save(T entity) {
        return this.entityManager.merge(entity);
    }

    public T find(Long id) {
        return this.entityManager.find(this.entityType, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityType.getSimpleName() + " e", entityType).getResultList();
    }

    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

}
