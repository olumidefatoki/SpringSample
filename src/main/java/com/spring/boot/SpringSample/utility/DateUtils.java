package com.spring.boot.SpringSample.utility;

import java.util.Optional;

public class DateUtils {
    public static void main(String[] args) {
    Optional<Object> empty = Optional.empty();
    if (empty.isPresent()){
        System.out.println("I am here");
    }
    }
}
