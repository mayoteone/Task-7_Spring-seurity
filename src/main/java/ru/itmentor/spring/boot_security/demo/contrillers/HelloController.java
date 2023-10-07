package ru.itmentor.spring.boot_security.demo.contrillers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
