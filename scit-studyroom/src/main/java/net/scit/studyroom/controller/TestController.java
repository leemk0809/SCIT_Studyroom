package net.scit.studyroom.controller;

import lombok.RequiredArgsConstructor;
import net.scit.studyroom.domain.Test;
import net.scit.studyroom.domain.TestRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor // final 객체를 Constructor Injection 해줌. (Autowired 역할)
public class TestController {
    private final TestRepository testRepository;

    @GetMapping("/test")
    public List<Test> findAllMember() {
        return testRepository.findAll();
    }
}
