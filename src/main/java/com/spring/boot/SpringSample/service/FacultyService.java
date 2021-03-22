package com.spring.boot.SpringSample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.SpringSample.domain.request.CreateFacultyRequest;
import com.spring.boot.SpringSample.domain.request.FacultyFilterRequest;
import com.spring.boot.SpringSample.domain.request.PaginationRequest;
import com.spring.boot.SpringSample.domain.request.UpdateFacultyRequest;
import com.spring.boot.SpringSample.domain.response.FacultyResponse;
import com.spring.boot.SpringSample.domain.response.Pagination;
import com.spring.boot.SpringSample.exception.NotFoundException;
import com.spring.boot.SpringSample.model.Faculty;
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
public class FacultyService {
    private final BaseRepository repo;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    public FacultyResponse createFaculty(CreateFacultyRequest request) {
        Faculty faculty = mapper.map(request, Faculty.class);
        faculty = repo.save(faculty);
       // log.debug("Create new Faculty - {}", new Gson().toJson(faculty));
        return mapper.map(faculty, FacultyResponse.class);
    }

    public FacultyResponse updateFaculty(UpdateFacultyRequest request, long FacultyId) {

        Faculty Faculty = repo.findOneOptional(Faculty.class, FacultyId)
                .orElseThrow(() -> new NotFoundException("Requested Faculty Id does not exist!"));
        mapper.map(request, Faculty);
        repo.update(Faculty);
        log.debug("Faculty record updated - {}", new Gson().toJson(Faculty));

        return mapper.map(Faculty, FacultyResponse.class);
    }

    public Pagination<FacultyResponse> fetchDepartment(FacultyFilterRequest request) {
        Map<String, Object> filter = new HashMap<>();
        if (request.getId() >0L){
            filter.put("id",request.getId());
        }
        PaginationRequest page = PaginationRequest.builder().page(request.getPage()).size(request.getSize()).build();
        Page<Faculty> response = repo.findAllBy(Faculty.class, filter, page);
        return mapper.map(response, new TypeToken<Pagination<FacultyResponse>>() {
        }.getType());
    }
}
