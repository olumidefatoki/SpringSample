package com.spring.boot.SpringSample.domain.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T> {

    private List<T> content;
    private int number;
    private int size;
    private long totalElements;
    private int totalPages;

    @Builder.Default
    private boolean last = Boolean.FALSE;


    @Builder.Default
    private boolean first = Boolean.FALSE;

    public Pagination(Page<T> results) {
        this.content = results.getContent();
        this.number = results.getNumber();
        this.size = results.getSize();
        this.totalElements = results.getNumberOfElements();
        this.totalPages = results.getTotalPages();
        this.last = results.isLast();
        this.first = results.isFirst();
    }
}
