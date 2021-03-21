package com.spring.boot.SpringSample.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacultyFilterRequest {
    private long id;
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 25;
}
