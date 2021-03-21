package com.spring.boot.SpringSample.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationRequest {

    @Builder.Default
    @Min(value = 1, message = "Page must be greater than zero (0)")
    private Integer page = 1;

    @Builder.Default
    @Size(max = 100, message = "Page size must be greater than 0 but less than 100")
    private Integer size = 25;


}
