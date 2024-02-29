//package com.example.securitydemo.controller;
//
//
//import lombok.Data;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotBlank;
//import java.util.Optional;
//
//@RestController
//@Validated
//public class PersonController {
//    @Data
//    public static class Input {
//
//        @NotBlank
//        String mailAddress;
//        String name;
//    }
//    @Data
//    public class ImageUploadData{
//        String name;
//        MultipartFile image;
//    }
//
//    @GetMapping("/hello2")
//    public ResponseEntity<?> getData() {
//        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
//    }
//
//    @PostMapping("/bye2")
//    public ResponseEntity<?> giveData(@RequestBody @Valid Input input) {
//        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
//    }
//
//    @PostMapping(value = "upload",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<?> uploadPhoto(ImageUploadData imageUploadData) {
//
//        return ResponseEntity.accepted().build();
//    }
//    @GetMapping(value = "download",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<?> downloadPhoto() {
//
//        return ResponseEntity.ok().body("");
//    }
//}
