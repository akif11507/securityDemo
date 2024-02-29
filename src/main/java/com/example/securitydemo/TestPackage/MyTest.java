package com.example.securitydemo.TestPackage;

import com.example.securitydemo.model.Singleton;
import com.example.securitydemo.repo.BookRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MyTest {
    @NonNull
    private final
    BookRepository bookRepository;

    public List<?> datafind(String[] args) {
//        Singleton.getInstance("", "");

        Pageable pageable = PageRequest.of(0, 3);
        Long val = 1L;
        System.out.println(bookRepository.findAllById(val, pageable));
        return new ArrayList<>();
    }
}
