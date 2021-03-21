package com.spring.boot.SpringSample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.text.MessageFormat;


@Repository
public class ValidationRepository {

    private final EntityManager entityManager;

    @Autowired
    public ValidationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean IsValidId(String tableName, long id) {

        String sqlQuery = "select e from " + tableName + " e where e.id = " + id;
        return entityManager.createQuery(sqlQuery).getResultList().isEmpty();
    }

    public boolean isUnique(String tableName, String columnName, Object value) {
        String sqlQuery = "select e from  " + tableName + " e where LOWER(e." + columnName + ") = LOWER(:value)";
        return entityManager.createQuery(sqlQuery)
                .setParameter("value", value)
                .getResultList()
                .isEmpty();
    }

    public boolean isExist(String tableName, String columnName, Object value) {
        String sqlQuery = MessageFormat.format("select e from  {0} e where LOWER(e.{1}) = LOWER(:value)", tableName, columnName);
        return entityManager.createQuery(sqlQuery)
                .setParameter("value", value)
                .getResultList()
                .isEmpty();
    }
}
