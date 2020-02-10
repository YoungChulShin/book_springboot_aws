package com.ycshin.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return  "index";    // index.mustache전환
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
