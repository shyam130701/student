package com.example.student.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Entity


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Student {
    @Id
    private Integer id;

    private String name;


    private String email;
    private String department;


}
