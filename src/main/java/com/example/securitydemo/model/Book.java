package com.example.securitydemo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "bookSource")
@Data
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String role;

}