package com.example.securitydemo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "jacks")
@Data
public class Jacks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    private int age;

}