package com.spring.boot.SpringSample.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    Long id;
    String name;
    Long departmentId;
    String HOD;
}
