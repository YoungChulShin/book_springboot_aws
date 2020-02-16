package com.ycshin.book.springboot.web;

import com.ycshin.book.springboot.config.auth.dto.SessionUser;
import com.ycshin.book.springboot.domain.user.User;
import com.ycshin.book.springboot.service.PostsService;
import com.ycshin.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser)httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return  "index";    // index.mustache전환
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
