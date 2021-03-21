package com.spring.boot.SpringSample.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.boot.SpringSample.validator.IsUnique;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateFacultyRequest {

    @NotNull(message = "Faculty Name is required")
    @IsUnique(tableName = "Faculty", columnName = "name", message = "Faculty name must be unique")

    private String name;

}
