package com.spring.boot.SpringSample.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;




@AllArgsConstructor
@Repository
public class CustomRepository {
    
    private final EntityManager em;
    
//
    

}
