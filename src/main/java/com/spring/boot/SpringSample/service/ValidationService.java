package com.spring.boot.SpringSample.service;


import com.spring.boot.SpringSample.repository.ValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final ValidationRepository repo;

    public boolean isValidId(String tableName, long id) {
        return repo.IsValidId(tableName, id);
    }

    public boolean isUnique(String tableName, String columnName, Object value) {
        return repo.isUnique(tableName, columnName, value);
    }

    public boolean isExist(String tableName, String columnName, Object value) {
        return repo.isExist(tableName, columnName, value);
    }
}
