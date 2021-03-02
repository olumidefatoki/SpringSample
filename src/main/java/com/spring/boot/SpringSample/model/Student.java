package com.spring.boot.SpringSample.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    @Column(name = "first_name")
    @Size(max = 255)
    String firstName;
    @Column(name = "last_name")
    @Size(max = 255)
    String lastName;
    @Size(max = 255)
    String email;
    @Column(name = "phone_number")
    @Size(max = 11)
    String phoneNumber;
    @Size(max = 255)
    String address;
    @Column(name = "department_id")
    @Size(max = 255)
    Long departmentId;
    @Column(name = "date_of_birth")
    @Size(max = 255)
    String dateOfBirth;
    @Size(max = 10)
    String gender;
}
