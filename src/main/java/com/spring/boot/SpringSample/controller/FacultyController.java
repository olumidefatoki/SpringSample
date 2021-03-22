package com.spring.boot.SpringSample.controller;

import com.spring.boot.SpringSample.domain.request.CreateFacultyRequest;
import com.spring.boot.SpringSample.domain.request.FacultyFilterRequest;
import com.spring.boot.SpringSample.domain.response.ApiResponse;
import com.spring.boot.SpringSample.domain.response.FacultyResponse;
import com.spring.boot.SpringSample.domain.response.Pagination;
import com.spring.boot.SpringSample.service.FacultyService;
import com.spring.boot.SpringSample.utility.AppConstants;
import com.spring.boot.SpringSample.utility.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstants.APP_VERSION + "faculty/")
public class FacultyController {
    private final FacultyService service;

    @PostMapping(path = "")
    public ResponseEntity<ApiResponse<FacultyResponse>> createFaculty(@Valid @RequestBody CreateFacultyRequest request) {
        FacultyResponse faculty = service.createFaculty(request);
        ApiResponse<FacultyResponse> response = ApiResponse.<FacultyResponse>builder()
                .message(ResponseMessage.SUCCESSFUL)
                .status(HttpStatus.OK.value()).data(faculty).error("").build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "")
    public ResponseEntity<ApiResponse<Pagination<FacultyResponse>>> fetchDepartment(FacultyFilterRequest request) {
        Pagination<FacultyResponse> newCorp = service.fetchDepartment(request);
        ApiResponse<Pagination<FacultyResponse>> response = ApiResponse.<Pagination<FacultyResponse>>builder()
                .message(ResponseMessage.SUCCESSFUL)
                .status(HttpStatus.OK.value()).data(newCorp).error("").build();
        return ResponseEntity.ok().body(response);
    }
}
