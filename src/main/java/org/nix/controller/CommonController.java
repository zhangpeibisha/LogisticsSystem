package org.nix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Kiss
 * @date 2018/04/14 19:49
 */
@Controller
public class CommonController {

    @GetMapping("/")
    public String index() {
        return "redirect:/templates/login.html";
    }
}
