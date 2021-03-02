package com.spring.boot.SpringSample.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
public class Department {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    @Size(max = 255)
    String name;
    @Size(max = 255)
    @Column(name = "head_of_faculty")
    String HOD;
}
