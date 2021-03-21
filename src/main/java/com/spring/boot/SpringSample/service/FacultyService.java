package com.spring.boot.SpringSample.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.SpringSample.domain.request.CreateFacultyRequest;
import com.spring.boot.SpringSample.domain.request.FacultyFilterRequest;
import com.spring.boot.SpringSample.domain.request.PaginationRequest;
import com.spring.boot.SpringSample.domain.response.FacultyResponse;
import com.spring.boot.SpringSample.domain.response.Pagination;
import com.spring.boot.SpringSample.model.Faculty;
import com.spring.boot.SpringSample.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FacultyService {
    private final BaseRepository repo;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    public FacultyResponse createIssuer(CreateFacultyRequest request) {
        Faculty faculty = mapper.map(request, Faculty.class);
        faculty = repo.save(faculty);
       // log.debug("Create new Issuer - {}", new Gson().toJson(faculty));
        return mapper.map(faculty, FacultyResponse.class);
    }

//    public IssuerResponse updateIssuer(UpdateIssuerRequest request, long issuerId) {
//
//        Issuers issuer = repo.findOneOptional(Issuers.class, issuerId)
//                .orElseThrow(() -> new NotFoundException("Requested Issuer Id does not exist!"));
//        mapper.map(request, issuer);
//        repo.update(issuer);
//        log.debug("Issuer record updated - {}", new Gson().toJson(issuer));
//
//        return mapper.map(issuer, IssuerResponse.class);
//    }

    public Pagination<FacultyResponse> queryIssuer(FacultyFilterRequest request) {
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
