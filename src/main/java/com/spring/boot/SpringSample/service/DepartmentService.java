package com.spring.boot.SpringSample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.SpringSample.domain.request.CreateDepartmentRequest;
import com.spring.boot.SpringSample.domain.request.DepartmentFilterRequest;
import com.spring.boot.SpringSample.domain.request.PaginationRequest;
import com.spring.boot.SpringSample.domain.response.DepartmentResponse;
import com.spring.boot.SpringSample.domain.response.DepartmentResponse;
import com.spring.boot.SpringSample.domain.response.Pagination;
import com.spring.boot.SpringSample.model.Department;
import com.spring.boot.SpringSample.model.Department;
import com.spring.boot.SpringSample.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class DepartmentService {

    private final BaseRepository repo;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {
        Department department = mapper.map(request, Department.class);
        log.info("department create request - {}", new Gson().toJson(department));
        department = repo.save(department);
        return mapper.map(department, DepartmentResponse.class);
    }

    public Pagination<DepartmentResponse> fetchDepartment(DepartmentFilterRequest request) {
        Map<String, Object> filter = new HashMap<>();
        if (request.getId() > 0L) {
            filter.put("id", request.getId());
        }
        if (null != request.getName()) {
            filter.put("id", request.getId());
        }
        PaginationRequest page = PaginationRequest.builder().page(request.getPage()).size(request.getSize()).build();

        Page<Department> response = repo.findAllBy(Department.class, filter, page);
        return mapper.map(response, new TypeToken<Pagination<DepartmentResponse>>() {
        }.getType());
    }
}
