//package com.example.securitydemo.controller;
//
//import com.example.securitydemo.model.Trees;
//import com.example.securitydemo.repo.BookRepository;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotBlank;
//
//@RestController
//@Validated
//@RequiredArgsConstructor
//public class BookController {
//
//    @Data
//    public static class Input {
//
//        @NotBlank
//        String data;
//        String phone;
//    }
//    private final BookRepository bookRepository;
//
//    Trees val = Trees.get(Trees.OAK.getValue());
//
////    Trees.OAK.getValue();
//
//
//    @GetMapping(value = "/hello"
//            , produces = MediaType.APPLICATION_XML_VALUE
//    )
//    public ResponseEntity<?> getData() {
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_XML)
//                .body(bookRepository.findAll());
//    }
//
//    @PostMapping("/bye")
//    public ResponseEntity<?> giveData(@RequestBody @Valid Input input) {
//        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
//    }
//}
