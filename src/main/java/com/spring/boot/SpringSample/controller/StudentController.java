package com.spring.boot.SpringSample.controller;

import com.spring.boot.SpringSample.service.FacultyService;
import com.spring.boot.SpringSample.utility.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstants.APP_VERSION + "faculty/")
public class StudentController {
    private final FacultyService service;
//
//    @PostMapping(path = "")
//    public ResponseEntity<ApiResponse<FacultyResponse>> createIssuer(@Valid @RequestBody CreateFacultyRequest request) {
//        FacultyResponse faculty = service.createIssuer(request);
//        ApiResponse<FacultyResponse> response = ApiResponse.<FacultyResponse>builder()
//                .message(ResponseMessage.SUCCESSFUL)
//                .status(HttpStatus.OK.value()).data(faculty).error("").build();
//        return ResponseEntity.ok().body(response);
//    }
//
//    @GetMapping(path = "")
//    public ResponseEntity<ApiResponse<Pagination<FacultyResponse>>> queryIssuer(FacultyFilterRequest request) {
//        Pagination<FacultyResponse> newCorp = service.queryIssuer(request);
//        ApiResponse<Pagination<FacultyResponse>> response = ApiResponse.<Pagination<FacultyResponse>>builder()
//                .message(ResponseMessage.SUCCESSFUL)
//                .status(HttpStatus.OK.value()).data(newCorp).error("").build();
//        return ResponseEntity.ok().body(response);
//    }
}
