package com.example.securitydemo.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "plants")
@Data
public class Plants {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String fname;
    private String lname;

}