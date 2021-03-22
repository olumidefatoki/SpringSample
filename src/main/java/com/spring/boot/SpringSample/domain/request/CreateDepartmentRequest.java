package com.spring.boot.SpringSample.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.boot.SpringSample.validator.IsUnique;
import com.spring.boot.SpringSample.validator.IsValidId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateDepartmentRequest {

    @NotNull(message = "Department Name is required")
    @IsUnique(tableName = "Department", columnName = "name", message = "Department name must be unique")
    private String name;

    @NotNull(message = "Faculty Id Name is required")
    @IsValidId(tableName = "Faculty", message = "Faculty ID does not exist")
    private long facultyId;

    @NotEmpty(message = "Head of department Name is required")
    private String HOD;

}
