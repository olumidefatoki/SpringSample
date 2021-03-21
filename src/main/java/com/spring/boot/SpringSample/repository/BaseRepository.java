package com.spring.boot.SpringSample.repository;


import com.spring.boot.SpringSample.domain.request.PaginationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;


@SuppressWarnings("ALL")
@Transactional
@Repository
@AllArgsConstructor
@Slf4j
public class BaseRepository {

    private final EntityManager em;
    // private final Javers javersObj;

    public <T> T findOne(Class<T> type, Long id) {
        T obj = em.find(type, id);
        if (!StringUtils.isEmpty(obj)) {
            em.detach(obj);
            return obj;
        }
        return null;
    }

    public <T> Optional<T> findOneOptional(Class<T> type, Long id) {
        T obj = em.find(type, id);
        if (!StringUtils.isEmpty(obj)) {
            em.detach(obj);
            return Optional.of(obj);
        }
        return Optional.empty();
    }

    public <T> T save(T entity) {
        em.persist(entity);
        em.flush();

        return entity;
    }

    @Transactional
    public <T> void refresh(T t) {
        em.refresh(t);
    }

    public <T> T update(T entity) {

        em.merge(entity);

        return entity;

    }

    public <T> T directUpdate(T entity) {
        em.merge(entity);

        return entity;
    }

    public <T> void delete(T entity) {
        em.remove(entity);
    }

    public <T> void detach(T entity) {
        em.detach(entity);
    }

    public <T> List<T> saveAll(Iterable<T> entities) {

        List<T> result = new ArrayList<>();

        for (T entity : entities) {
            result.add(save(entity));
        }

        return result;
    }

    public <T> List<T> findAllById(Class<T> classz, Iterable<T> ids) {
        TypedQuery<T> typeQuery = em.createQuery("SELECT k FROM " + classz.getSimpleName() + " k WHERE k.id in :Ids",
                classz);
        typeQuery.setParameter("Ids", ids);
        return typeQuery.getResultList();
    }

    public <T, K> List<T> findAllById(Class<T> classz, Set<K> ids) {
        TypedQuery<T> typeQuery = em.createQuery("SELECT k FROM " + classz.getSimpleName() + " k WHERE k.id in :Ids",
                classz);
        typeQuery.setParameter("Ids", ids);
        return typeQuery.getResultList();
    }

    public <T> List<T> findAllBy(Class<T> classz, String columnName, Object value) {
        String sqlQuery = "select e from  " + classz.getSimpleName() + " e where LOWER(e." + columnName
                + ") = LOWER(:value)";
        TypedQuery<T> typeQuery = em.createQuery(sqlQuery, classz).setParameter("value", value);

        return typeQuery.getResultList();
    }


    public <T, V, K> Optional<T> findOneByOptional(Class<T> classz, V columnName, K value) {
        String sqlQuery = "select e from  " + classz.getSimpleName() + " e where LOWER(e." + columnName
                + ") = LOWER(:value)";

        TypedQuery<T> typeQuery = em.createQuery(sqlQuery, classz).setParameter("value", value);
        return Optional.ofNullable(typeQuery.getSingleResult());
    }

    public <T> Optional<T> findOneByOptional(Class<T> classz, Map<String, Object> filter) {

        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("select e from  " + classz.getSimpleName() + " e where");
        filter.keySet().forEach(i -> sqlQuery.set(sqlQuery.get() + " e." + i + " = :" + i + " AND"));
        sqlQuery.set(sqlQuery.get().substring(0, sqlQuery.get().length() - 4));
        log.info("SLQ: {}", sqlQuery.get());

        TypedQuery<T> typeQuery = em.createQuery(sqlQuery.get(), classz);

        filter.forEach(typeQuery::setParameter);

        return Optional.ofNullable(typeQuery.getSingleResult());
    }

    //The filter map object takes care of both OR, AND & LIKE; 
    //If the value is of type List means it represents OR; 
    //If the value is of type Map means it represents LIKE;
    //By default all value are chained with AND
    @SuppressWarnings("unchecked")
    public <T> Page<T> findAllBy(Class<T> classz, Map<String, Object> filterTemp, PaginationRequest page) {

        Map<String, Object> filter = new HashMap<>(filterTemp);

        AtomicReference<String> sqlQuery = new AtomicReference<>();
        sqlQuery.set("select e from  " + classz.getSimpleName() + " e " + (filter.isEmpty() ? "" : "where "));


        filter.keySet().stream().filter(o -> !(filter.get(o) instanceof List<?>))
                .forEach(i -> sqlQuery.set(sqlQuery.get() + " " + i + " = :" + i + " AND"));

        filter.keySet().stream().filter(o -> (filter.get(o) instanceof Map))
                .forEach(i -> ((Map<String, String>) filter.get(i)).keySet().stream().forEach(k -> {
                    String value = ((Map<String, String>) filter.get(i)).get(k);
                    sqlQuery.set(sqlQuery.get() + " " + k + " LIKE :" + value + " AND");
                }));


        for (String s : filter.keySet()) {
            if ((filter.get(s) instanceof List<?>)) {
                sqlQuery.set(sqlQuery.get() + "(");
                IntStream.range(0, ((List<?>) filter.get(s)).size()).forEach(index -> {
                    sqlQuery.set(sqlQuery.get() + " " + s + " = :" + s + "" + index + " OR");
                });
                sqlQuery.set(sqlQuery.get().substring(0, sqlQuery.get().length() - 3) + ") AND");
            }
        }

        sqlQuery.set(filter.isEmpty() ? sqlQuery.get() : sqlQuery.get().substring(0, sqlQuery.get().length() - 4));

        TypedQuery<Long> countQuery = em.createQuery(sqlQuery.get().replace("select e from", "select count(e) from"),
                Long.class);
        TypedQuery<T> typeQuery = em.createQuery(sqlQuery.get(), classz);

        filter.keySet().stream().filter(i -> !(filter.get(i) instanceof List<?>)).forEach(i -> {
            typeQuery.setParameter(i, filter.get(i));
            countQuery.setParameter(i, filter.get(i));
        });

        filter.keySet().stream().filter(o -> (filter.get(o) instanceof List<?>)).forEach(i -> {
            List<?> values = (List<?>) filter.get(i);

            IntStream.range(0, values.size()).forEach(index -> {
                typeQuery.setParameter(i + "" + index, values.get(index));
                countQuery.setParameter(i + "" + index, values.get(index));
            });
        });

        log.info("SLQ: {}", sqlQuery.get());
        Long contentSize = countQuery.getSingleResult();
        page.setSize(page.getSize() == 0 ? (contentSize.intValue() == 0 ? 1 : contentSize.intValue()) : page.getSize());

        typeQuery.setFirstResult((page.getPage() - 1) * page.getSize()).setMaxResults(page.getSize());

        return new PageImpl<>(typeQuery.getResultList(), PageRequest.of(page.getPage() - 1, page.getSize()),
                contentSize);
    }

}
