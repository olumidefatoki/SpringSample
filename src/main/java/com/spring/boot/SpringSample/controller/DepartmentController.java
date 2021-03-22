package com.spring.boot.SpringSample.controller;

import com.spring.boot.SpringSample.domain.request.CreateDepartmentRequest;
import com.spring.boot.SpringSample.domain.request.DepartmentFilterRequest;
import com.spring.boot.SpringSample.domain.response.ApiResponse;
import com.spring.boot.SpringSample.domain.response.DepartmentResponse;
import com.spring.boot.SpringSample.domain.response.Pagination;
import com.spring.boot.SpringSample.service.DepartmentService;
import com.spring.boot.SpringSample.utility.AppConstants;
import com.spring.boot.SpringSample.utility.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstants.APP_VERSION + "department/")
public class DepartmentController {
    private final DepartmentService service;

    @PostMapping(path = "")
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        DepartmentResponse department = service.createDepartment(request);
        ApiResponse<DepartmentResponse> response = ApiResponse.<DepartmentResponse>builder()
                .message(ResponseMessage.SUCCESSFUL)
                .status(HttpStatus.OK.value()).data(department).error("").build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "")
    public ResponseEntity<ApiResponse<Pagination<DepartmentResponse>>> fetchDepartment(DepartmentFilterRequest request) {
        Pagination<DepartmentResponse> newCorp = service.fetchDepartment(request);
        ApiResponse<Pagination<DepartmentResponse>> response = ApiResponse.<Pagination<DepartmentResponse>>builder()
                .message(ResponseMessage.SUCCESSFUL)
                .status(HttpStatus.OK.value()).data(newCorp).error("").build();
        return ResponseEntity.ok().body(response);
    }
}
