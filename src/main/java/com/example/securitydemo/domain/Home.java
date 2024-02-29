//package com.example.securitydemo.domain;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
////@Builder
//@RequiredArgsConstructor
//@Table(name = "home")
//public class Home {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;
//    String name;
//    String address;//could be normalized
//    @OneToMany(targetEntity = Car.class,mappedBy = "home")
//    List<Car> cars;
//}
