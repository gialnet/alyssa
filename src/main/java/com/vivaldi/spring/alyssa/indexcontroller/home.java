package com.vivaldi.spring.alyssa.indexcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class home {

    @GetMapping("/")
    public String root(HttpSession session) {

        log.info("login '{}'", session.getAttribute("uuid"));
        return "login";
    }

    @GetMapping("/index")
    public String index(HttpSession session) {

        log.info("index '{}'", session.getAttribute("email"));
        return "index";
    }

    @GetMapping("/contact")
    public String contact(HttpSession session) {

        log.info("contact '{}'", session.getAttribute("email"));
        return "contact";
    }

}
